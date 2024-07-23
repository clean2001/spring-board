package com.beyond.board.post.controller;

import com.beyond.board.post.dto.*;
import com.beyond.board.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/post")
@Controller
public class PostController {

    private final PostService postService;

    @GetMapping("/create")
    public String createPost() {
        return "post/post_create";
    }

    @PostMapping("/create")
    public String createPost(CreatePostReqDto createPostReqDto) {
        CreatePostResDto post = postService.createPost(createPostReqDto);
        return "redirect:/post/list";
    }


    // 전체 조회
//    @GetMapping("/list")
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
            return "post/post_detail";
        } catch(IllegalArgumentException e) {
            return null; // 존재하지 않음
        }
    }

    // 수정
    @PostMapping("/update/{postId}")
    public String updatePost(@PathVariable Long postId, UpdatePostReqDto updatePostReqDto) {
        postService.updatePost(postId, updatePostReqDto);

        return "redirect:/post/detail/" + postId;
    }

    // 삭제
    @GetMapping("/delete/{postId}")
    public String deletePost(@PathVariable Long postId) {
        postService.deletePost(postId);

        return "redirect:/post/list";
    }

    // 페이지네이션 연습
    @GetMapping("/rest/list/page")
    @ResponseBody
    // pageable에 default set을 지정 가능. 페이징 처리는 부하를 주지 않기 위해 **필수적**으로 해야한다.
    // Pageable 요청 방법: localhost:8080/post/list/page?size=10&page=0 => 이런식으로. (데이터
    public Page<PostListResDto> postListTest(@PageableDefault(size = 10, sort = "createdTime", direction = Sort.Direction.DESC) Pageable pageable) {
        return postService.postListPage(pageable);
    }


    // 페이지네이션 연습 - mvc version
    @GetMapping("/list")
    // pageable에 default set을 지정 가능. 페이징 처리는 부하를 주지 않기 위해 **필수적**으로 해야한다.
    // Pageable 요청 방법: localhost:8080/post/list/page?size=10&page=0 => 이런식으로. (데이터
    public String postListMvc(Model model, @PageableDefault(size = 10, sort = "createdTime", direction = Sort.Direction.DESC) Pageable pageable) {
        model.addAttribute("postList", postService.postList(pageable));
        return "post/post_list";
    }
}