package com.beyond.board.author.dto;

import com.beyond.board.author.domain.Role;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AuthorResDto {
    private Long id;
    private String name;
    private String email;
    private Role role;

}
