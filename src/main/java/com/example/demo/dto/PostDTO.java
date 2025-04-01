// 코드 리펙토링 (Lombok의 어노테이션)
// DTO
package com.example.demo.dto;

import lombok.*;

@Data               // getter, setter, toString, equals, hashCode를 자동 생성
@NoArgsConstructor  // 기본 생성자 (매개변수 없는 생성자) 프레임워크(JPA, JSON 변환 라이브러리 등)에서 객체를 생성할 때 필요
@AllArgsConstructor // 모든 필드를 받는 생성자 (객체를 한번에 초기화 할 때 유용
@Builder            // 빌더 패턴을 통한 유연한 객체 생성 // Lombok에서 제공하는 어노테이션
public class PostDTO {
    private String name;
    private String email;
    private String password;
}

// DTO를 사용하여 회원가입 요청에서 이름, 이메일, 패스워드만 필요하여 이 정보만 전달
