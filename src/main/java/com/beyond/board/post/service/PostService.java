package com.beyond.board.post.service;

import com.beyond.board.author.domain.Author;
import com.beyond.board.author.repository.AuthorRepository;
import com.beyond.board.post.domain.Post;
import com.beyond.board.post.dto.*;
import com.beyond.board.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PostService {
    private final PostRepository postRepository;
    private final AuthorRepository authorRepository;

    //
    public CreatePostResDto createPost(CreatePostReqDto createPostReqDto) {
        System.out.println(createPostReqDto.getEmail());
        Author author = authorRepository.findByEmail(createPostReqDto.getEmail()).orElseThrow(() -> new NoSuchElementException("없는 회원입니다."));
        Post post = createPostReqDto.toEntity(author);

        Post savedPost = postRepository.save(post);

        return CreatePostResDto.toResponse(post, author);
    }

    // 전체 조회
    public List<PostListResDto> getPostList() {
        List<Post> postList = postRepository.findAll();

        return postList.stream()
                .map(PostListResDto::fromEntity)
                .collect(Collectors.toList());
    }

    public PostDetailResDto getPostDetail(Long postId) {
        Post post = postRepository.findByIdOrThrow(postId);

        return PostDetailResDto.fromEntity(post);
    }

    // 수정
    @Transactional
    public void updatePost(Long id, UpdatePostReqDto updatePostReqDto) {
        Post post = postRepository.findByIdOrThrow(id);
        post.updatePost(updatePostReqDto); // 수정
//        postRepository.save(post); // Transaction 리드 온리 여도 이건 들어가넹
    }

    // 삭제
    public void deletePost(Long id) {
        postRepository.deleteById(id);
    }
}
