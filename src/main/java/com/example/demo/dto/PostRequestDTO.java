package com.example.demo.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostRequestDTO {
    /**
     * 회원 가입/수정 요청 시 필요한 필드들
     * (예: 이름, 이메일, 패스워드)
     */
    private String name;
    private String email;
    private String password;
}

// 클라이언트가 POST, PUT등의 요청으로 보낼 때 사용하는 DTO임
// 클라이언트 -> 서버 로 들어오는 데이터 구조!!