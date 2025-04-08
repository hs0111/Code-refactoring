//코드 리펙토링 (try/catch) 이용해 예외를 처리함

package com.example.demo.controller;

import com.example.demo.dto.PostRequestDTO;
import com.example.demo.dto.PostResponseDTO;
import com.example.demo.entity.PostEntity;
import com.example.demo.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
// 모든 API 엔드 포인트가 /users 를 기본 경로로 갖도록 설정

public class PostController {
    private final PostService postService;


    @Autowired//생성자 주입 :
    //Autowired을 통해 postService를 받아 서비스 계층과 통신

    public PostController(PostService postService) {
        this.postService = postService;
    }

    // 회원가입 (생성)
    // **ResponseEntity** 사용하여 응답상태 코드와 헤더를 세밀하게 제어함!!!!
    // 1. HTTP 상태코드와 헤더를 직접 지정할 수 있다(명확한 정보 전달 가능)
    // 2. 예외 처리 (HTTP 상태코드와 오류 메세지 반환)
    // 3. 일관된 응답 형식 유지

    @PostMapping     // 제네릭 와일드카드 (어떤 타입이드 올 수 있음)
    public ResponseEntity<?> createUser(@RequestBody PostRequestDTO requestDTO) {
        // 클라이언트가 회원 가입 정보를 담은 JSON 데이터를 보내면 @RequestBody PostDTO로 빋음
        try {
            PostEntity createdUser = postService.createUser(requestDTO);
            // 회원가입 성공 시, 200 OK 응답과 함께 생성된 객체 반환
            PostResponseDTO responseDTO = PostResponseDTO.builder()
                    //DTO 객체를 생성할 때 생성자 호출 방식보다 빌더 패턴을 사용하면 필드의 순서를 사용하지 않고
                    //명시적으로 각 필드의 값을 설정할 수 있어 코드 가독성이 높아짐
                    .id(createdUser.getId())
                    .name(createdUser.getName())
                    .email(createdUser.getEmail())
                    .build();
                    // 어떤 값이 어떤 필드에 들어가는지 쉽개 파악 ㄱㄴ

            return ResponseEntity.ok(responseDTO);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        // try/catch 블록을 사용해 예외 발생 시 적절한 에러 메시지를 반환 (클라이언트는 무엇이 잘못되었는지 알 수 있음)

    }

    // 모든 회원 조회
    @GetMapping
    // 파라미터 없이 GET / users 요청 -> 모든 회원 정보 목록 반환
    public ResponseEntity<List<PostEntity>> getAllUsers() {
        List<PostEntity> users = postService.getAllUsers();
        return ResponseEntity.ok(users);
    } // 예외 처리 필요 없이 단순 조회 결과를 ResponseEntity.ok(users) 로 반환

    // 특정 회원 조회
    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        try {
            PostEntity entity = postService.getUserById(id);
            // 엔티티 -> ResponseDTO 변환
            PostResponseDTO responseDTO = PostResponseDTO.builder()
                    .id(entity.getId())
                    .name(entity.getName())
                    .email(entity.getEmail())
                    .build();
            return ResponseEntity.ok(responseDTO);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    // 회원 정보 수정
    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody PostRequestDTO requestDTO) {
        // URL의 {id}와 수정할 데이터를 JSON 형태의 PostDTO로 받아 해당 회원 정보를 수정
        try {
            PostEntity updatedUser = postService.updateUser(id, requestDTO);
            PostResponseDTO responseDTO = PostResponseDTO.builder()
                    .id(updatedUser.getId())
                    .name(updatedUser.getName())
                    .email(updatedUser.getEmail())
                    .build();
            return ResponseEntity.ok(responseDTO);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // 회원 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        try {
            postService.deleteUser(id);
            return ResponseEntity.ok("회원이 정상적으로 삭제되었습니다.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}


// ResponseEntity 활용 ; 메서드에서 성공 및 실패 상채에 따라 HTTP 상태 코드(200, 400등)
// 명확하게 반환하기 때문에 API사용자가 음답 상태를 쉽게 파악함
