package kr.co.sboard.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Builder
@Table( name = "Article")
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int no;

    @Column(nullable = false)
    private String cate;

    private String title;
    private String content;
    private int comment;
    private int file;
    private int hit;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "writer")
    private User user;

    private String regip;

    @PrePersist
    public void prePersist() {
        if(this.cate==null){
            this.cate = "free";
        }
    }

    // 추가 필드
    //@Transient // 엔티티 속성에서 테이블 매핑에서 제외하는 어노테이션
    //private String nick;

    @OneToMany(mappedBy = "ano")  // mappedBy 속성은 매핑되는 엔티티의 FK 컬럼이다.
    private List<File> files;

    @CreationTimestamp
    private LocalDateTime wdate;

}
