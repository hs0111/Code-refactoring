package com.example.demo.repository;

import com.example.demo.entity.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<PostEntity, Long> {

    /**
     * 주어진 이메일로 회원(PostEntity)을 조회합니다.
     *
     * @param email 조회할 이메일 주소
     * @return 해당 이메일에 해당하는 회원 정보가 있으면 Optional<PostEntity>에 감싸서 반환하고, 없으면 Optional.empty() 반환
     */
    Optional<PostEntity> findByEmail(String email);
}
