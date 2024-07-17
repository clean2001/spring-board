package com.beyond.board.author.controller;

import com.beyond.board.author.domain.Author;
import com.beyond.board.author.dto.AuthorResDto;
import com.beyond.board.author.dto.CreateAuthorReqDto;
import com.beyond.board.author.service.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/author")
@RestController
public class AuthorController {
    private final AuthorService authorService;
    // 생성, 리스트 조회, 상세 조회

    // 생성
    @PostMapping("/create")
    public AuthorResDto createAuthor(@RequestBody CreateAuthorReqDto createAuthorReqDto) {
        return authorService.createAuthor(createAuthorReqDto);
    }


    // 전체 조회
    @GetMapping
    public List<AuthorResDto> getAuthorList() {
        return authorService.getAuthorList();
    }


    // 상세 조회
    @GetMapping("/detail/{authorId}")
    public AuthorResDto getAuthorDetail(@PathVariable Long authorId) {
        try {
            return authorService.getAuthorDetail(authorId);
        } catch(IllegalArgumentException e) {
            return null; // 존재하지 않음
        }
    }

}
