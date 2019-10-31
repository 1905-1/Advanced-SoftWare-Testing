package name.ealen.interfaces.dto;

import name.ealen.domain.entity.Address;
import name.ealen.domain.entity.Article;

public class ArticleResponse {
    private Article article;
    private boolean issupport;
    private boolean isconcerned;
    private String avatarUrl;
    private String nickName;
    private int AuthorId;
    private Address address;

    public int getAuthorId() {
        return AuthorId;
    }

    public void setAuthorId(int authorId) {
        AuthorId = authorId;
    }

    public boolean isIsconcerned() {
        return isconcerned;
    }

    public void setIsconcerned(boolean isconcerned) {
        this.isconcerned = isconcerned;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

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
