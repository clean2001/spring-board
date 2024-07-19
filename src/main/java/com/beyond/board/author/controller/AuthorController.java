package com.beyond.board.author.controller;

import com.beyond.board.author.domain.Author;
import com.beyond.board.author.dto.AuthorDetailResDto;
import com.beyond.board.author.dto.AuthorResDto;
import com.beyond.board.author.dto.CreateAuthorReqDto;
import com.beyond.board.author.service.AuthorService;
import com.beyond.board.post.dto.UpdateAuthorReqDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/author")
@Controller
public class AuthorController {
    private final AuthorService authorService;
    // 생성, 리스트 조회, 상세 조회

    @GetMapping("/register")
    public String createPost() {
//        CreatePostResDto post = postService.createPost(createPostReqDto);
        return "author/author_register";
    }

    // 생성
    @PostMapping("/register")
    public String createAuthor(CreateAuthorReqDto createAuthorReqDto) {
        AuthorResDto author = authorService.createAuthor(createAuthorReqDto);
        return "redirect:/author/list";
    }

    // 전체 조회
    @GetMapping("/list")
    public String getAuthorList(Model model) {
        List<AuthorResDto> authorList = authorService.getAuthorList();
        model.addAttribute("authorList", authorList);
        return "author/author_list";
    }


    // 상세 조회
    @GetMapping("/detail/{authorId}")
    public String getAuthorDetail(@PathVariable Long authorId, Model model) {
        try {
            AuthorDetailResDto authorDetail = authorService.getAuthorDetail(authorId);
            model.addAttribute("author", authorDetail);
            return "author/author_detail";
        } catch (IllegalArgumentException e) {
            return null; // 존재하지 않음
        }

    }

    @GetMapping("/delete/{id}")
    public String deleteAuthor(@PathVariable Long id, Model model) {
        authorService.deleteAuthor(id);
        return "redirect:/author/list";
    }

    @PostMapping("/update/{id}")
    public String updateAuthor(@PathVariable Long id, UpdateAuthorReqDto updateAuthorReqDto) {
        authorService.updateAuthor(id, updateAuthorReqDto);

        return "redirect:/author/detail/" + id;
    }

}
