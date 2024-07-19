package com.beyond.board.post.domain;

import com.beyond.board.author.domain.Author;
import com.beyond.board.common.BaseTimeEntity;
import com.beyond.board.post.dto.UpdatePostReqDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
public class Post extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String title;
    @Column(length = 3000)
    private String contents;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private Author author;

    public Post(String title, String contents, Author author) {
        this.title = title;
        this.contents = contents;
        this.author = author;
    }

    public void updatePost(UpdatePostReqDto updatePostReqDto) {
        this.title = updatePostReqDto.getTitle();
        this.contents = updatePostReqDto.getContents();
    }
}
