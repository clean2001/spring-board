package com.beyond.board.author.dto;

import com.beyond.board.author.domain.Author;
import com.beyond.board.author.domain.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class AuthorDetailResDto {
    private Long id;
    private String name;
    private LocalDateTime createdTime;
    private String email;
    private String password;
    private Role role;
    private int postCount;

    public static AuthorDetailResDto fromEntity(Author author) {
        return AuthorDetailResDto.builder()
                .id(author.getId())
                .name(author.getName())
                .createdTime(author.getCreatedTime())
                .email(author.getEmail())
                .password(author.getPassword())
                .role(author.getRole())
                .postCount(author.getPosts().size())
                .build();
    }
}
