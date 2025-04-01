package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "post_entity") // 명시적으로 테이블 이름을 지정하여 DB에 생성될 테이블 이름을 "post_entity"로 고정
@Data // getter, setter, equals, hashCode, toString 메서드를 자동 생성하여 보일러플레이트 코드 제거
@NoArgsConstructor // JPA에서 엔티티 객체를 생성할 때 기본 생성자가 필요하므로 명시적으로 생성
@AllArgsConstructor // 모든 필드를 인자로 받는 생성자를 자동 생성 (필요한 경우 사용)
@Builder // 빌더 패턴을 적용하여 객체 생성 시 필드들을 선택적으로 설정할 수 있게 하여, 코드 가독성과 유연성을 높임
public class PostEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // 데이터베이스가 직접 기본 키를 생성하도록 함

    @Setter(AccessLevel.NONE) // id는 DB에서 자동 생성되므로 외부에서 수정하지 못하도록 제한
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;
}
