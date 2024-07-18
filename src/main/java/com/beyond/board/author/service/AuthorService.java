package com.beyond.board.author.service;

import com.beyond.board.author.domain.Author;
import com.beyond.board.author.dto.AuthorDetailResDto;
import com.beyond.board.author.dto.AuthorResDto;
import com.beyond.board.author.dto.CreateAuthorReqDto;
import com.beyond.board.author.repository.AuthorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true) // 조회 작업시에 readOnly하면 성능 향상된다. 하지만 저장,
public class AuthorService {
    private final AuthorRepository authorRepository;

    public List<AuthorResDto> getAuthorList() {
        return authorRepository.findAll().stream()
                .map(Author::fromEntity)
                .collect(Collectors.toList());
    }

    @Transactional
    public AuthorResDto createAuthor(CreateAuthorReqDto createAuthorReqDto) {
        Author author = createAuthorReqDto.toEntity();

        // 이메일 검증
        Optional<Author> authorOpt = authorRepository.findByEmail(createAuthorReqDto.getEmail());
        if(authorOpt.isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 이메일");
        }

        Author savedAuthor = authorRepository.save(author);

        return savedAuthor.fromEntity();
    }

    public AuthorDetailResDto getAuthorDetail(Long authorId) {
        Author author = authorRepository.findByIdOrThrow(authorId);
        return AuthorDetailResDto.fromEntity(author);
    }

    // service 똔느 리보지
    // service 레이어에 있는 코드가 고도화 되고 복잡할 경우 service를 import

    public Author authorFindByEmail(String email) {
        return authorRepository.findByEmail(email).orElseThrow(() -> new EntityNotFoundException("존재하지 않는 이메일"));
    }
}
