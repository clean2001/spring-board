package com.beyond.board.author;

import com.beyond.board.author.domain.Author;
import com.beyond.board.author.domain.Role;
import com.beyond.board.author.repository.AuthorRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional // 롤백처리를 위해서 저장!
public class AuthorRepositoryTest {
    @Autowired
    private AuthorRepository authorRepository;

    // 리포지토리는 실제로 문제가 생길일이 거의 없음 ㄱㅊㄱㅊ
    @Test
    public void authorSaveTest() {
        // 테스트 원리 : save -> 재조회
        // 준비 단계(prepare, given)
        Author author = Author.builder()
                .name("hong2")
                .email("hong2@naver.com")
                .password("1234")
                .role(Role.ADMIN)
                .build();

        // 실행(execute, when)
        authorRepository.save(author);
        Author savedAuthor = authorRepository.findByEmail("hong2@naver.com").orElse(null);

        // 검증(then)
        Assertions.assertEquals(author.getEmail(), savedAuthor.getEmail());
    }

}
