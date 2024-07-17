package com.beyond.board.author.service;

import com.beyond.board.author.domain.Author;
import com.beyond.board.author.dto.AuthorResDto;
import com.beyond.board.author.dto.CreateAuthorReqDto;
import com.beyond.board.author.repository.AuthorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class AuthorService {
    private final AuthorRepository authorRepository;

    public List<AuthorResDto> getAuthorList() {
        return authorRepository.findAll().stream()
                .map(Author::fromEntity)
                .collect(Collectors.toList());
    }

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

    public AuthorResDto getAuthorDetail(Long authorId) {
        Author author = authorRepository.findByIdOrThrow(authorId);
        return author.fromEntity();
    }
}
