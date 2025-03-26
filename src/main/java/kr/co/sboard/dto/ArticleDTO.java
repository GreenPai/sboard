package kr.co.sboard.dto;

import lombok.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ArticleDTO {

    private int no;
    private String cate;
    private String title;
    private String content;
    private int comment;
    private int file;
    private int hit;
    private String writer;
    private String regip;
    private String wdate;

    private MultipartFile file1;
    private MultipartFile file2;

    public List<MultipartFile> getMultipartFile(){
        return List.of(file1, file2);
    }

}
