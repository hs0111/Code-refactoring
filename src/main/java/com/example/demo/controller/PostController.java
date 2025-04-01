//코드 리펙토링 (try/catch) 이용해 예외를 처리함

package com.example.demo.controller;

import com.example.demo.dto.PostDTO;
import com.example.demo.entity.PostEntity;
import com.example.demo.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<?> createUser(@RequestBody PostDTO postDTO) {
        // 클라이언트가 회원 가입 정보를 담은 JSON 데이터를 보내면 @RequestBody PostDTO로 빋음
        try {
            PostEntity createdUser = postService.createUser(postDTO);
            // 회원가입 성공 시, 200 OK 응답과 함께 생성된 객체 반환
            return ResponseEntity.ok(createdUser);
        } catch (Exception e) {
            // 예외 발생 시, 400 Bad Request와 에러 메시지 반환
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        // try/catch 블록을 사용해 예외 발생 시 적절한 에러 메시지를 반환
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
    public ResponseEntity<postEntity> getUser(@PathVariable Long id) {
        // URL 경로의 {id} 부분을 @PathVariable로 받음 -> 특정 회원 정보 조회
        try {
            PostEntity user = postService.getUserById(id);
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } // 조회 실패하면 예외 발생 -> catch 블록에서 에러 메세지 반환
    }

    // 회원 정보 수정
    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody PostDTO postDTO) {
        // URL의 {id}와 수정할 데이터를 JSON 형태의 PostDTO로 받아 해당 회원 정보를 수정
        try {
            PostEntity updatedUser = postService.updateUser(id, postDTO);
            return ResponseEntity.ok(updatedUser);
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

}

// ResponseEntity 활용 ; 메서드에서 성공 및 실패 상채에 따라 HTTP 상태 코드(200, 400등)
// 명확하게 반환하기 때문에 API사용자가 음답 상태를 쉽게 파악함
