package com.beyond.board.post.controller;

import com.beyond.board.post.dto.CreatePostReqDto;
import com.beyond.board.post.dto.CreatePostResDto;
import com.beyond.board.post.dto.PostDetailResDto;
import com.beyond.board.post.dto.PostListResDto;
import com.beyond.board.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/post")
@Controller
public class PostController {

    private final PostService postService;

    @GetMapping("/register")
    public String createPost() {
        return "post/post_register";
    }

    @PostMapping("/register")
    public String createPost(CreatePostReqDto createPostReqDto) {
        CreatePostResDto post = postService.createPost(createPostReqDto);
        return "redirect:post/post_list";
    }


    // 전체 조회
    @GetMapping("/list")
    public String getPostList(Model model) {
        List<PostListResDto> postList = postService.getPostList();
        model.addAttribute("postList", postList);
        return "post/post_list";
    }


    // 상세 조회
    @GetMapping("/detail/{postId}")
    public String getPostDetail(@PathVariable Long postId, Model model) {
        try {
            PostDetailResDto postDetail = postService.getPostDetail(postId);
            model.addAttribute("post", postDetail);
            return "post/post_register";
        } catch(IllegalArgumentException e) {
            return null; // 존재하지 않음
        }
    }
}