package com.beyond.board.author.service;

import com.beyond.board.author.domain.Author;
import com.beyond.board.author.dto.AuthorDetailResDto;
import com.beyond.board.author.dto.AuthorResDto;
import com.beyond.board.author.dto.CreateAuthorReqDto;
import com.beyond.board.author.repository.AuthorRepository;
import com.beyond.board.post.domain.Post;
import com.beyond.board.post.dto.UpdateAuthorReqDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
//@Transactional(readOnly = true) // 조회 작업시에 readOnly하면 성능 향상된다. 하지만 저장,
public class AuthorService {
    private final AuthorRepository authorRepository;

    public List<AuthorResDto> getAuthorList() {
        return authorRepository.findAllByDeletedTime(null).stream()
                .map(Author::fromEntity)
                .collect(Collectors.toList());
    }

    @Transactional
    public AuthorResDto createAuthor(CreateAuthorReqDto createAuthorReqDto) {
        Author author = createAuthorReqDto.toEntity();

        //== CASCADE 테스트!!! ==//
        // cascade persist 테스트. remove 테스트는 회원 삭제로 대체
        author.getPosts().add(Post.builder()
                .title("가입인사")
                .author(author)
                .contents("안녕하세요"+createAuthorReqDto.getName()+"입니다.")
                .build());

        // 이메일 검증
        Optional<Author> authorOpt = authorRepository.findByEmail(createAuthorReqDto.getEmail());
        if(authorOpt.isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 이메일");
        }

        // 비밀번호 검증
        checkPasswordLength(createAuthorReqDto.getPassword());

        Author savedAuthor = authorRepository.save(author);

        return savedAuthor.fromEntity();
    }

    public AuthorDetailResDto getAuthorDetail(Long authorId) {
        Author author = authorRepository.findByIdOrThrow(authorId);
        return AuthorDetailResDto.fromEntity(author);
    }

    // service 또는 리포지토리에 의존성
    // service 레이어에 있는 코드가 고도화 되고 복잡할 경우 service를 import

    public Author authorFindByEmail(String email) {
        return authorRepository.findByEmail(email).orElseThrow(() -> new EntityNotFoundException("존재하지 않는 이메일"));
    }

    // 삭제
    @Transactional
    public void deleteAuthor(Long id) {
        System.out.println("line 58");
        Author author = authorRepository.findByIdOrThrow(id);
        authorRepository.delete(author); // 삭제
        System.out.println("삭제됨??");
    }

    @Transactional
    public void updateAuthor(Long id, UpdateAuthorReqDto updateAuthorReqDto) {
        // 업데이트는 무조건 객체 찾아와야함
        Author author = authorRepository.findByIdOrThrow(id);
        // 비밀번호 검증
        checkPasswordLength(updateAuthorReqDto.getPassword());
        // dirty checking 시 @Transactional 어노테이션이 필요하다. => 확인이 필요함
        // 테스트 결과:
        // @Transactional(readOnly = true): 이거 하면 더티 체킹 안됨
        // @Transactional: 이거해야 변경 감지해서 저장된다.
        // @Transactional 안붙이면? 저장안됨
        author.changeInfo(updateAuthorReqDto); // 이름, 패스워드 바꾸기 => 아 save 안해줘도 변경 감지가 되나보네?
    }

    private void checkPasswordLength(String password) {
        if(password.length() < 8) {
            throw new IllegalArgumentException("비밀번호는 8자리 이상이어야 합니다.");
        }
    }
}
