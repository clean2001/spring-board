package com.beyond.board.author.repository;

import com.beyond.board.author.domain.Author;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthorRepository extends JpaRepository<Author, Long> {

    default Author findByIdOrThrow(Long authorId) {
        return findById(authorId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));
    }

    Optional<Author> findByEmail(String email);
}
