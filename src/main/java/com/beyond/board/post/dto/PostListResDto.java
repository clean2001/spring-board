package com.beyond.board.post.dto;

import com.beyond.board.author.domain.Author;
import com.beyond.board.post.domain.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostListResDto {
    private Long id;
    private String title;
    private String contents;
    // Author 객체 그 자체를 return 하게 되면 Author 안에 Post가 재참조되어 순환참조 문제가 발생한다.
    private String authorEmail;
    private String authorName;
//    private Author author;

    public static PostListResDto fromEntity(Post post) {
        return PostListResDto.builder()
                .id(post.getId())
                .title(post.getTitle())
                .contents(post.getContents())
                .authorEmail(post.getAuthor().getEmail())
                .authorName(post.getAuthor().getName())
//                .author(post.getAuthor()) // FIXME: 삭제 예정
                .build();
    }
}
