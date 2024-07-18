package com.beyond.board.post.repository;

import com.beyond.board.post.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.persistence.EntityNotFoundException;

public interface PostRepository extends JpaRepository<Post, Long> {
    default Post findByIdOrThrow(Long postId) {
        return findById(postId).orElseThrow(() -> new EntityNotFoundException("없는 포스트입니다."));
    }
}
