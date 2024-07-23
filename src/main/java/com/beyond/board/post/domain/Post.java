package com.beyond.board.post.domain;

import com.beyond.board.author.domain.Author;
import com.beyond.board.common.BaseTimeEntity;
import com.beyond.board.post.dto.CreatePostReqDto;
import com.beyond.board.post.dto.CreatePostResDto;
import com.beyond.board.post.dto.UpdatePostReqDto;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@ToString
@Builder
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
    private String appointment; // 예약 여부 Y, N
    private LocalDateTime appointmentTime;

    // 연관 관계의 주인은 fk가 있는 Post이다!!
    @ManyToOne(fetch = FetchType.LAZY) // ManyToOne은 N+1 이슈를 해결하기 위해 FetchType.LAZY를 설정해준다.
    @JoinColumn(name = "author_id")
    private Author author;

    public Post(CreatePostReqDto createPostReqDto, Author author) {
        System.out.println("line 51: " + createPostReqDto);
        System.out.println("line 39: " + createPostReqDto.getAppointmentTime());

        // 예약 시간 변환하기
        LocalDateTime dateTime = null;
        if(!createPostReqDto.getAppointmentTime().isEmpty() && createPostReqDto.getAppointment().equals("Y")) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
            dateTime = LocalDateTime.parse(createPostReqDto.getAppointmentTime(), formatter);
        }

        // appointmentTime이 now보다 이전이면
        if(dateTime != null && dateTime.isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("시간 입력이 잘못되었습니다.");
        }

        this.title = createPostReqDto.getTitle();
        this.contents = createPostReqDto.getContents();
        this.appointment = createPostReqDto.getAppointment();
        this.appointmentTime = dateTime; // 예약 O이면 예약시간 있음. 아니면 null
        this.author = author;
    }

    public void updatePost(UpdatePostReqDto updatePostReqDto) {
        this.title = updatePostReqDto.getTitle();
        this.contents = updatePostReqDto.getContents();
    }

    public void updateAppointment(String appointment) {
        this.appointment = appointment;
    }
}
