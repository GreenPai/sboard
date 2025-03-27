package kr.co.sboard.service;

import com.querydsl.core.Tuple;
import kr.co.sboard.dao.ArticleMapper;
import kr.co.sboard.dto.ArticleDTO;
import kr.co.sboard.dto.PageRequestDTO;
import kr.co.sboard.dto.PageResponseDTO;
import kr.co.sboard.entity.Article;
import kr.co.sboard.entity.User;
import kr.co.sboard.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.swing.text.html.parser.Entity;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final ModelMapper modelMapper;
    private final ArticleMapper articleMapper;

    public PageResponseDTO findAll(PageRequestDTO pageRequestDTO){

        // 페이징 처리를 위한 pageable 객체 생성
        Pageable pageable = pageRequestDTO.getPageable("no");

        Page<Tuple> pageArticle = articleRepository.selectAllForList(pageable);
        // article Entity 리스트를 ArticleDTO 리스트로 변환


        List<ArticleDTO> articleDTOList = pageArticle.getContent().stream().map(tuple -> {

            Article article = tuple.get(0, Article.class);
            String nick = tuple.get(1, String.class);
            //article.setNick(nick);

            ArticleDTO articleDTO = modelMapper.map(article, ArticleDTO.class);
            articleDTO.setNick(nick);
            return articleDTO;
        }).toList();

        int total = (int) pageArticle.getTotalElements();

        return PageResponseDTO.builder()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(articleDTOList)
                .total(total)
                .build();

    }

    public PageResponseDTO searchAll(PageRequestDTO pageRequestDTO){

        // 페이징 처리를 위한 pageable 객체 생성
        Pageable pageable = pageRequestDTO.getPageable("no");

        Page<Tuple> pageArticle = articleRepository.selectAllForSearch(pageable, pageRequestDTO);
        // article Entity 리스트를 ArticleDTO 리스트로 변환

        List<ArticleDTO> articleDTOList = pageArticle.getContent().stream().map(tuple -> {

            Article article = tuple.get(0, Article.class);
            String nick = tuple.get(1, String.class);
            //article.setNick(nick);

            ArticleDTO articleDTO = modelMapper.map(article, ArticleDTO.class);
            articleDTO.setNick(nick);
            return articleDTO;
        }).toList();

        int total = (int) pageArticle.getTotalElements();

        return PageResponseDTO.builder()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(articleDTOList)
                .total(total)
                .build();

    }

    public int register(ArticleDTO articleDTO){

        // 엔티티 변환
        User user = User.builder()
                .uid(articleDTO.getWriter())
                .build();

        // 엔티티 변환
        Article article= modelMapper.map(articleDTO, Article.class);
        article.setUser(user);

        log.info("Article DTO: {}", article);

        // JPA 저장
        // Article savedArticle = articleRepository.save(article);

        //Mybatis 저장'
        articleMapper.insertArticle(articleDTO);

        // 매개변수로 전달되는 articleDTO의 속성에 mybatis가 INSERT한 데이터의 pk를 반환
        int no = articleDTO.getNo();
        return no;
    }

    public ArticleDTO findById(int no){
        Optional<Article> optArticle = articleRepository.findById(no);

        if(optArticle.isPresent()){
            Article article = optArticle.get();
            ArticleDTO articleDTO= modelMapper.map(article, ArticleDTO.class);
            return articleDTO;
        }
        return null;
    }


}
