package com.beyond.board.author.dto;

import com.beyond.board.author.domain.Author;
import com.beyond.board.author.domain.Role;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class CreateAuthorReqDto {
    private String name;
    private String email;
    private String password;
    private Role role;


    public Author toEntity() {
        return new Author(this.name, this.email, this.password, this.role);
    }
}
