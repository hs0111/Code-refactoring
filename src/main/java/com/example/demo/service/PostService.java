package com.example.demo.service;

import com.example.demo.dto.PostRequestDTO;
import com.example.demo.entity.PostEntity;
import com.example.demo.repository.PostRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {

    private final PostRepository postRepository;

    // 생성자 주입: PostRepository를 서비스 계층에 주입합니다.
    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    /**
     * 회원가입(Create)
     * 요청 DTO에 담긴 데이터를 검증한 후, 엔티티 객체를 생성하여 저장합니다.
     * @param requestDTO 클라이언트가 보낸 회원가입 데이터(PostRequestDTO)
     * @return 저장된 PostEntity 객체
     * @throws Exception 입력 데이터가 부적합한 경우 예외 발생
     */
    public PostEntity createUser(PostRequestDTO requestDTO) throws Exception {
        // 입력 값 검증: 필수 항목이 누락되었는지 확인

        // if문: 처음부터 요청 객체 자체가 null인지 검사 ->
        // 문제가 있는 입력을 조기에 감지하여 빠르게 예외 발생을 할 수 있음
        if (requestDTO == null) {
            throw new Exception("요청 데이터가 없습니다.");
        }
        if (requestDTO.getName() == null || requestDTO.getName().trim().isEmpty()) {
            throw new Exception("이름이 비어있습니다.");
        }
        if (requestDTO.getEmail() == null || requestDTO.getEmail().trim().isEmpty()) {
            throw new Exception("이메일이 비어있습니다.");
        }
        if (requestDTO.getPassword() == null || requestDTO.getPassword().trim().isEmpty()) {
            throw new Exception("패스워드가 비어있습니다.");
        }
        // 중복 이메일 체크(Guard Clause, 사전 조사 검사)
        if (postRepository.findByEmail(requestDTO.getEmail()).isPresent()) {
            //DB에 이미 해당 파일이 존재하는지 미리 확인함
            // 존재하는 이메일일시 예외를 발생시켜 중복 데이터가 삽입되지 않도록 함

            throw new Exception("이미 존재하는 이메일입니다.");
        }

        // 엔티티 객체 생성: Lombok의 @Builder를 사용하여 유연하게 객체를 생성합니다
        // 각 필드의 이름을 명시하면거 객체를 생성할 수 있음 1.코드의 가독성 2.수정 편리함
        PostEntity user = PostEntity.builder()
                .name(requestDTO.getName())
                .email(requestDTO.getEmail())
                .password(requestDTO.getPassword())
                .build();

        return postRepository.save(user);
    }

    /**
     * 모든 회원 조회(Read All)
     * @return 모든 회원 데이터가 담긴 리스트
     */
    public List<PostEntity> getAllUsers() {
        return postRepository.findAll();
    }

    /**
     * 특정 회원 조회(Read by ID)
     * 주어진 id로 회원을 찾고, 없으면 예외를 발생시킵니다.
     * @param id 조회할 회원의 ID
     * @return 해당 회원의 PostEntity 객체
     * @throws Exception 회원이 존재하지 않으면 Exception 발생
     */
    public PostEntity getUserById(Long id) throws Exception {
        if (id == null) {
            throw new Exception("회원 ID가 제공되지 않았습니다.");
        }
        return postRepository.findById(id)
                .orElseThrow(() -> new Exception("해당 회원을 찾을 수 없습니다."));
    }

    /**
     * 회원 정보 수정(Update)
     * 기존 회원을 id로 조회한 후, 요청 DTO의 값이 유효하면 해당 값을 업데이트하고 저장합니다.
     * @param id 수정할 회원의 ID
     * @param requestDTO 클라이언트가 보낸 업데이트 데이터(PostRequestDTO)
     * @return 업데이트 된 PostEntity 객체
     * @throws Exception 회원이 존재하지 않거나, 업데이트 데이터가 부적합한 경우 예외 발생
     */
    public PostEntity updateUser(Long id, PostRequestDTO requestDTO) throws Exception {
        // 1. id 값이 제공되지 않은 경우 즉시 예외 발생
        if (id == null) {
            throw new Exception("회원 ID가 제공되지 않았습니다.");
        }
        // 2. 업데이트할 데이터(requestDTO)가 null이면 예외 발생
        if (requestDTO == null) {
            throw new Exception("업데이트할 데이터가 없습니다.");
        }
        // 기존 회원 조회 3. 데이터베이스에서 해당 id로 회원을 조회하고 없으면 예외를 발생
        PostEntity existingUser = postRepository.findById(id)
                .orElseThrow(() -> new Exception("해당 회원이 존재하지 않습니다."));

        // 4. 각 필드를 개별적으로 검증하고, 유효한 경우에만 기존 회원 데이터를 업데이트

        // 4-1. 이름(name) 업데이트 검사
        // requestDTO의 name 필드가 null이 아니고 공백이 아닌 경우에만 업데이트
        if (requestDTO.getName() != null && !requestDTO.getName().trim().isEmpty()) {
            existingUser.setName(requestDTO.getName());
        }
        // 4-2. 이메일(email) 업데이트 검사
        if (requestDTO.getEmail() != null && !requestDTO.getEmail().trim().isEmpty()) {
            // 만약 이메일 변경이 있다면, 중복 체크
            if (!existingUser.getEmail().equals(requestDTO.getEmail())
                    && postRepository.findByEmail(requestDTO.getEmail()).isPresent()) {
                // 이미 사용 중인 이메일 있다면 예외 발생
                throw new Exception("이미 사용 중인 이메일입니다.");
            }
            // 중복되지 않으면 이메일 업데이트
            existingUser.setEmail(requestDTO.getEmail());
        }

        // 4-3. 패스워드(password) 업데이트 검사
        // requestDTO의 password 필드가 null이 아니고, 공백이 아닌 경우에만 업데이트
        if (requestDTO.getPassword() != null && !requestDTO.getPassword().trim().isEmpty()) {
            existingUser.setPassword(requestDTO.getPassword());
        }

        // 5. 변경된 객체를 데이터베이스에 저장하고, 업데이트 된 엔티티를 반환
        return postRepository.save(existingUser);
    }
    // 1. 입력 값 초기 검증
    // 2. 회원 정보 여부 확인 postRepository.findById(id).orElseThrow(() -> new Exception("해당 회원이 존재하지 않습니다."));
    //                     주어진 ID를 가진 회원이 데이터베이스에 존재하는지 확인함
    // 3. 필드별  업데이트 검증 및 적용


    /**
     * 회원 삭제(Delete)
     * 주어진 id에 해당하는 회원이 존재하는지 확인 후, 삭제합니다.
     * @param id 삭제할 회원의 ID
     * @throws Exception 삭제할 회원이 존재하지 않으면 Exception 발생
     */
    public void deleteUser(Long id) throws Exception {
        if (id == null) {
            throw new Exception("회원 ID가 제공되지 않았습니다.");
        }
        if (!postRepository.existsById(id)) {
            throw new Exception("삭제할 회원이 존재하지 않습니다.");
        }
        postRepository.deleteById(id);
    }
}

// 입력된 데이터가 부적합할 경우를 미리 잡아내어 안전하게 API를 제공할 수 있으며
// 유지보수나 코드 확장 시에도 유연하게 대응할 수 있음!!