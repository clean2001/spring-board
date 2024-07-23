package com.beyond.board.post.repository;

import com.beyond.board.post.domain.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.persistence.EntityNotFoundException;
import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    default Post findByIdOrThrow(Long postId) {
        return findById(postId).orElseThrow(() -> new EntityNotFoundException("없는 포스트입니다."));
    }

    // jpql을 적용하여 네이밍 룰을 통한 방식이 아닌 메서드 생성

    // select p.*, a.* from post p left join author a on p.author_id = a.id;
    @Query("select p from Post p left join fetch p.author")
    List<Post> findAllFetch();


    // select p.* from post p left join author a on p.author_id = a.id;
    // 아래의 join문은 author 객체를 통한 조건문으로 Post를 필터링할 때 사용한다.
    // ex. 이름이 'kim'인 사람의 글을 가져오겠다.
    // 얘는 author를 참조하는 순간 N+1 문제 발생
    @Query("select p from Post p left join p.author")
    List<Post> findAllLeftJoin();

    // Page<Post> : List<Post> + 해당 요소의 Page 정보
    // Pageable: 몇 번 페이지?(default=0, 0번부터 시작) + 한 페이지에 몇개?(default=20)+ 정렬 조건
    // 페이징 처리를 하는 이유: 부하 발생을 시키지 않기 위해서
    // select * from post VS select count(*) from post;
    Page<Post> findAll(Pageable pageable);

    Page<Post> findAllByAppointment(Pageable pageable, String appointment);
}
