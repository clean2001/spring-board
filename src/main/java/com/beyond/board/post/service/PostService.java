package com.beyond.board.post.service;

import com.beyond.board.author.domain.Author;
import com.beyond.board.author.repository.AuthorRepository;
import com.beyond.board.post.domain.Post;
import com.beyond.board.post.dto.CreatePostReqDto;
import com.beyond.board.post.dto.CreatePostResDto;
import com.beyond.board.post.dto.PostDetailResDto;
import com.beyond.board.post.dto.PostListResDto;
import com.beyond.board.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
        Author author = authorRepository.findById(createPostReqDto.getAuthorId()).orElseThrow(() -> new NoSuchElementException("없는 회원입니다."));
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
}
