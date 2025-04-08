package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "post_entity") // 테이블 이름을 명시적으로 지정 (필요 시 사용)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE) // ID는 DB에서 자동 생성되므로 setter를 노출하지 않음
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;



    // 이 필드를 통해 PostEntity(예: 게시글, 프로필, 또는 회원 상세정보와 관련된 추가 정보)가
    // 어느 UserEntity와 연관되어 있는지 나타냅니다.
    @ManyToOne(fetch = FetchType.LAZY)
    // Many-to-One 관계: 여러 PostEntity가 하나의 UserEntity에 속할 수 있음.( 1:N )
    // 하나의 UserEntity에 여러 개의 PostEntity가 연결되는 1:N (One-to-Many) 관계
    // 구성하기 위해 @ManyToOne 애너테이션을 추가

    @JoinColumn(name = "user_id") // post_entity 테이블에 외래키 컬럼 'user_id' 생성
    //어떤 UserEntity에 속하는지 데이터베이스에 저장함

    private UserEntity user;
}

//  UserEntity, PostEntity (기존에 있던거 모두 사용 가능)
//  두개를 매핑 관계로 연결(1:n, 1:1, 상관없음) Entity 설계해오기

