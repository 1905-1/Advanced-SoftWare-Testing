package name.ealen.application;

import name.ealen.domain.entity.Article;
import name.ealen.interfaces.dto.ArticleDTO;
import name.ealen.interfaces.dto.ArticleResponse;

import java.util.List;

public interface ArticleService {

    public List<ArticleResponse> getHotArticle(int lookUserId);

    public List<ArticleResponse>  getArticleByAddress(String addressId,int lookUserId);

    public Article getArticleById(int id,int lookUserId);

    public List<ArticleResponse> getArticleByUserId(int id,int lookUserId);

    public Article addArticle(ArticleDTO article);
}
