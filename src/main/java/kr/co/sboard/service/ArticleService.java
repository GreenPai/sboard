package kr.co.sboard.service;

import kr.co.sboard.dao.ArticleMapper;
import kr.co.sboard.dto.ArticleDTO;
import kr.co.sboard.entity.Article;
import kr.co.sboard.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.swing.text.html.parser.Entity;

@Slf4j
@RequiredArgsConstructor
@Service
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final ModelMapper modelMapper;
    private final ArticleMapper articleMapper;

    public int register(ArticleDTO articleDTO){

        // 엔티티 변환
        Article article= modelMapper.map(articleDTO, Article.class);
        log.info("Article DTO: {}", article);

        // JPA 저장
        // Article savedArticle = articleRepository.save(article);

        // Mybatis 저장'
        articleMapper.insertArticle(articleDTO);

        // 매개변수로 전달되는 articleDTO의 속성에 mybatis가 INSERT한 데이터의 pk를 반환
        int no = articleDTO.getNo();
        return no;
    }


}
