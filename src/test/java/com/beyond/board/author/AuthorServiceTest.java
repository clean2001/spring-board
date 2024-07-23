package com.beyond.board.author;

import com.beyond.board.author.domain.Author;
import com.beyond.board.author.domain.Role;
import com.beyond.board.author.dto.AuthorResDto;
import com.beyond.board.author.dto.CreateAuthorReqDto;
import com.beyond.board.author.service.AuthorService;
import com.beyond.board.post.dto.UpdateAuthorReqDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static com.beyond.board.author.domain.Role.*;

// 서비스 레이어에서의 검증은 중요하다!!!
@SpringBootTest
@Transactional
//@Rollback // 이거 안붙여도 롤백 다 된다
public class AuthorServiceTest {
    @Autowired
    private AuthorService authorService;

    // 저장 및 디테일 조회
    @Test
    void saveAndFind() {

        // given
        CreateAuthorReqDto authorReqDto = CreateAuthorReqDto.builder()
                .name("honggildong")
                .email("hong@hanmail.net")
                .password("123456789") // 비밀번호를 짧게 하면 에러 난다
                .role(ADMIN)
                .build();

        // when - 저장
        AuthorResDto authorResDto = authorService.createAuthor(authorReqDto);
        Author author = authorService.authorFindByEmail(authorResDto.getEmail());


        // then - 비교
        Assertions.assertEquals(authorReqDto.getEmail(), authorResDto.getEmail());
        Assertions.assertEquals(authorReqDto.getName(), authorResDto.getName());
        Assertions.assertEquals(authorReqDto.getRole(), authorResDto.getRole());
        Assertions.assertNotNull(authorResDto.getId());

        Assertions.assertEquals(authorReqDto.getEmail(), authorResDto.getEmail()); // 찾기 검증
    }




    // update 검증

    // 객체 create => 수정 작업 진행(name, password 바꾸기) => 수정 후 재조회 => Name, password가 잘 바뀌었는지 확인
    @Test
    void updateAuthorTest() {
        // given
        CreateAuthorReqDto authorReqDto = CreateAuthorReqDto.builder()
                .name("honggildong")
                .email("hong@hanmail.net")
                .password("123456789")
                .role(ADMIN)
                .build();

        authorService.createAuthor(authorReqDto); // 저장

        // when
        final String changedName = "홍씨";
        final String changedPassword = "9876543210";
        UpdateAuthorReqDto updateAuthorReqDto = UpdateAuthorReqDto.builder()
                .name(changedName)
                .password(changedPassword)
                .build();

        Author author = authorService.authorFindByEmail(authorReqDto.getEmail());
        authorService.updateAuthor(author.getId(), updateAuthorReqDto); // 업데이트
        Author updatedAuthor = authorService.authorFindByEmail(authorReqDto.getEmail()); // 다시 조회

        //then - 비교
        Assertions.assertEquals(updatedAuthor.getName(), changedName);
        Assertions.assertEquals(updatedAuthor.getPassword(), changedPassword);
    }

    // findAll 검증
}
