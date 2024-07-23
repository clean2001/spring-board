package com.beyond.board.post.dto;

import com.beyond.board.author.domain.Author;
import com.beyond.board.post.domain.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class CreatePostResDto {
    private Long id;
    private String title;
    private String contents;
    private Long authorId;
    private String authorEmail;
    private String authorName;
    private LocalDateTime createdTime;
    private String appointment; // 예약 여부 Y, N
    private LocalDateTime appointmentTime;

    public static CreatePostResDto fromEntity(Post post, Author author) {
        return CreatePostResDto.builder()
                .id(post.getId())
                .title(post.getTitle())
                .contents(post.getContents())
                .authorId(author.getId())
                .authorEmail(author.getEmail())
                .authorName(author.getName())
                .createdTime(post.getCreatedTime())
                .appointment(post.getAppointment())
                .appointmentTime(post.getAppointmentTime())
                .build();
    }

}
