package kr.co.sboard.dao;

import kr.co.sboard.dto.ArticleDTO;
import kr.co.sboard.entity.Article;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ArticleMapper {

    public int insertArticle(ArticleDTO articleDTO);
}
