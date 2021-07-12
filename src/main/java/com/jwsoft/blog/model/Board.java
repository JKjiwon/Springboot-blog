package com.jwsoft.blog.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.web.PageableDefault;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto_increment
    private int id;

    @Column(nullable = false, length = 100)
    private String title;

    @Lob // 대용량 데이터
    private String content; // 섬머노트 라이브러리 <html>태그가 섞여서 디자인 됨.

    private int count; // 조회수ㅡㅡㅐ

    @ManyToOne(fetch = FetchType.EAGER) // Many = Board , One = User
    @JoinColumn(name = "userId")
    private User user; // DB는 오브젝트를 저장할 수 없다. FK , 자바는 오브젝트를 저장할 수 있다.

    @OneToMany(mappedBy = "board", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    // mappedBy 연관관계의 주인이 아니다 (난 FK가 아니야) // DB에 칼럼을 만들지 마라, Board 를 select 할 때 join문을 통해서 값을 줘라.
    @JsonIgnoreProperties({"board"}) // 무한 참조 방지 -> board를 통해 reply를 호출 할때 board속성을 무시한다.
    @OrderBy("id desc")
    private List<Reply> replies;

    @CreationTimestamp // 데이터가 insert, update 될때 자동으로 시간이 들어간다.
    private Timestamp createDate;
}

// fetch = FetchType.EAGER : (간절히 바라는 ~) Board 테이블을 조회하면 User 테이블을 가져올게, 한건 밖에 없으니까.
// fetch = FetchType.LAZY : 필요하면 들고올게, 엄청나게 많은 건이 있을 수 있다.
