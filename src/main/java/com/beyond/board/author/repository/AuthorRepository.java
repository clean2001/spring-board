package com.beyond.board.author.repository;

import com.beyond.board.author.domain.Author;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface AuthorRepository extends JpaRepository<Author, Long> {

    // findBy 컬럼명의 규칙으로 자동으로 where 조건문을 사용한 쿼리 생성
    // 그 외: findByNameAndEmail, findByNameOrEmail
    // findByAgeBetween(int start, int end)
    // findByAgeLessThan(int age), findByAgeGreaterThan(int age), findByAgeGreaterThanEqual
    // findByAgeLessThan(int age), findByAgeGreaterThan(int age), findByAgeGreaterThanEqual
    // findByAgeIsNull, findByAgeIsNotNull => null 여부로 찾는 것이기 때문에 매개변수가 필요 없다.
    // findAllOrderByEmail();

    default Author findByIdOrThrow(Long authorId) {
        return findById(authorId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));
    }

    Optional<Author> findByEmail(String email);

    List<Author> findAllByDeletedTime(LocalDateTime time);
}
