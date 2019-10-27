package name.ealen.domain.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

/**
 * 实体 属性描述 用户评论
 */
@Entity
@Table
@Data
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 自增长策略
    @Column(name = "commentID", nullable = false)
    public long commentID;

    //评论内容
    public String content;
    //发表时间
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date date;

    @ManyToOne(cascade = {CascadeType.MERGE,CascadeType.REFRESH},optional = false)
    @JoinColumn(name = "articleId")
    private Article article;

    @ManyToOne(cascade = {CascadeType.MERGE,CascadeType.REFRESH},optional = false)
    @JoinColumn(name = "id")
    private WxAccount wxAccount;

    public long getCommentID() {
        return commentID;
    }

    public void setCommentID(long commentID) {
        this.commentID = commentID;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Article getArticle() {
        return article;
    }

    @JsonBackReference
    public void setArticle(Article article) {
        this.article = article;
    }

    public WxAccount getWxAccount() {
        return wxAccount;
    }

    @JsonBackReference
    public void setWxAccount(WxAccount wxAccount) {
        this.wxAccount = wxAccount;
    }
}
