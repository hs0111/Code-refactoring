package com.example.demo.service;

import com.example.demo.dto.PostDTO;
import com.example.demo.entity.PostEntity;
import com.example.demo.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PostService {

    private static PostRepository postRepository = null;

    @Autowired
    public PostService(PostRepository postRepository) {
        PostService.postRepository = postRepository;
    }

    // Create (회원가입)
    public PostEntity createUser(PostDTO postDTO) {
        PostEntity user = new PostEntity(postDTO.getName(), postDTO.getEmail(), postDTO.getPassword());
        return postRepository.save(user);
    }

    // Read (모든 회원 조회)
    public List<PostEntity> getAllUsers() {
        return postRepository.findAll();
    }

    // Read (특정 회원 조회)
    public static PostEntity getUserById(Long id) {
        return postRepository.findById(id).orElse(null);
    }

    // Update
    public PostEntity updateUser(Long id, PostDTO postDTO) {
        PostEntity user = postRepository.findById(id).orElseThrow();
        user.setName(postDTO.getName());
        user.setEmail(postDTO.getEmail());
        user.setPassword(postDTO.getPassword());
        return postRepository.save(user);
    }

    // Delete
    public void deleteUser(Long id) {
        postRepository.deleteById(id);
    }
}