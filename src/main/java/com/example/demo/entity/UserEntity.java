package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "user_entity") // 테이블 이름을 명시적으로 지정
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;

    @Column(nullable = false)
    private String username;

    // One-to-Many 관계: 한 UserEntity는 여러 PostEntity를 가질 수 있음.
    // mappedBy는 PostEntity의 'user' 필드와 매핑됨.
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PostEntity> posts;
    // posts 필드는 하나의 회원(UserEntity)이 여러PostEntity를 가질 수 있음을 나타냄

}
// 1. 책임 분리
// UserEntity를 도입하면, 회원의 기본 정보(예: 사용자 이름, 로그인 정보 등)는
// UserEntity에, 그리고 PostEntity는 회원과 연관된 게시글이나 추가 정보(예: 회원 상세 프로필, 연관 게시물 등)를
// 관리하는 식으로 역할을 명확히 분리할 수 있음

//