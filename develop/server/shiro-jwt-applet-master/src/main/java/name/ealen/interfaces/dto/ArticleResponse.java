package name.ealen.interfaces.dto;

import name.ealen.domain.entity.Article;

public class ArticleResponse {
    private Article article;
    private boolean issupport;
    
    public ArticleResponse(Article article) {
        this.article = article;
    }

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    public boolean isIssupport() {
        return issupport;
    }

    public void setIssupport(boolean issupport) {
        this.issupport = issupport;
    }
}
