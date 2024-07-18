package com.beyond.board.post.dto;

import com.beyond.board.post.domain.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import net.bytebuddy.asm.Advice;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class PostDetailResDto {
    private Long id;
    private String title;
    private String contents;
    private Long authorId;
    private String authorName;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;

    public static PostDetailResDto fromEntity(Post post) {
        return PostDetailResDto.builder()
                .id(post.getId())
                .title(post.getTitle())
                .contents(post.getTitle())
                .authorId(post.getAuthor().getId())
                .authorName(post.getAuthor().getName())
                .createdTime(post.getCreatedTime())
                .updatedTime(post.getUpdatedTime())
                .build();
    }
}
