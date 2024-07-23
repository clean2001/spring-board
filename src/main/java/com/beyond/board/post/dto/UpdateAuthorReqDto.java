package com.beyond.board.post.dto;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UpdateAuthorReqDto {
    private String name;
    private String password;
}
