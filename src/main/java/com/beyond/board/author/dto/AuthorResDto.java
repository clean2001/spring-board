package com.beyond.board.author.dto;

import com.beyond.board.author.domain.Role;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AuthorResDto { // Dto는 @Data 깔고 가도 된다.
    private Long id;
    private String name;
    private String email;
    private Role role;
    private LocalDateTime createdTime;

}
