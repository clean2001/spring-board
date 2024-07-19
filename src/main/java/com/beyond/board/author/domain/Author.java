package com.beyond.board.author.domain;

import com.beyond.board.author.dto.AuthorResDto;
import com.beyond.board.common.BaseTimeEntity;
import com.beyond.board.post.domain.Post;
import com.beyond.board.post.dto.UpdateAuthorReqDto;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Author extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(unique = true, nullable = false)
    private String email;

    private String password;

    @Enumerated(EnumType.STRING)
    Role role;

    // 이거는 필수 아님. 하지만 Author 쪽에서는 필수 이다.
    @OneToMany(mappedBy = "author")
    private List<Post> posts;


    public AuthorResDto fromEntity() {
        return AuthorResDto.builder()
                .id(this.id)
                .name(this.name)
                .email(this.email)
                .role(this.role)
                .createdTime(this.getCreatedTime())
                .build();
    }

    @Builder
    public Author(String name, String email, String password, Role role) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
    }


    public void changeDeletedTime() {
        this.deletedTime = LocalDateTime.now();
    }

    public void changeInfo(UpdateAuthorReqDto updateAuthorReqDto) {
        this.name = updateAuthorReqDto.getName();
        this.password = updateAuthorReqDto.getPassword();
    }
}
