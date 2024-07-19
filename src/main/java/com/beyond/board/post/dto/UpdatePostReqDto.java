package com.beyond.board.post.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdatePostReqDto {
    private String title;
    private String contents;
}
