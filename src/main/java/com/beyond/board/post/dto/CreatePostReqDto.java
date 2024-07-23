package com.beyond.board.post.dto;

import com.beyond.board.author.domain.Author;
import com.beyond.board.post.domain.Post;
import lombok.*;

import java.time.LocalDateTime;

@ToString
@Getter
@Setter
@NoArgsConstructor
public class CreatePostReqDto {
    private String title;
    private String contents;
    private String email;
    private String appointment; // 예약 여부 Y, N
    private String appointmentTime; // String으로 받은 뒤 변환
//    private LocalDateTime appointmentTime;

    public Post toEntity(Author author) {
        return new Post(this, author);
    }
}
