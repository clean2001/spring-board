package com.beyond.board.post.dto;

import com.beyond.board.author.domain.Author;
import com.beyond.board.post.domain.Post;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class CreatePostReqDto {
    private String title;
    private String contents;
    private String email;

    public Post toEntity(Author author) {
        return new Post(title, contents, author);
    }
}
