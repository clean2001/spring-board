package com.beyond.board.author.dto;

import com.beyond.board.author.domain.Author;
import com.beyond.board.author.domain.Role;
import lombok.*;

import java.util.ArrayList;

@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class CreateAuthorReqDto {
    private String name;
    private String email;
    private String password;
    private Role role;


    // builder 패턴
    public Author toEntity() {
        return Author.builder()
                .name(this.name)
                .email(this.email)
                .password(this.password)
                .role(this.role)
                .posts(new ArrayList<>())
                .build();
    }
}
