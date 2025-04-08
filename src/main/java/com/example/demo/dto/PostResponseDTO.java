package com.example.demo.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostResponseDTO {
    /**
     * 서버가 클라이언트에 응답할 때 사용하는 필드들
     * (예: id, name, email 등)
     */
    private Long id;
    private String name;
    private String email;
    // password를 응답에서 제외할 수도 있음 (보안 고려)
}
// 서버가 클라이언트에게 반환할 때(GET, POST 응답) 사용하는 DTO임
// 서버 -> 클라이언트 로 나가는 데이터 구조
