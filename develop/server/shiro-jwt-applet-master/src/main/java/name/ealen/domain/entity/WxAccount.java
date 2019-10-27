package name.ealen.domain.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import name.ealen.interfaces.dto.wxUserInfo;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * 实体 属性描述 这里只是简单示例，你可以自定义相关用户信息
 */
@Entity
@Table
@Data
public class WxAccount {

    @Id
    @GeneratedValue
    private Integer id;
    private String wxOpenid;
    private String sessionKey;
    private String nickname;
    private String avatarUrl;
    private String province;
    private String city;
    private String country;
    private int gender;
    private String language;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @LastModifiedDate
    private Date lastTime;

    //级联保存、更新、删除、刷新;延迟加载。当删除用户，会级联删除该用户的所有文章
    //拥有mappedBy注解的实体类为关系被维护端
    //mappedBy="author"中的author是Article中的wxAccount属性
    @JsonIgnore
    @OneToMany(mappedBy = "wxAccount",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<Article> articleList; //文章列表

    @OneToMany(mappedBy = "wxAccount",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Comment> commentList;//评论列表

    //用户自身关注的多对多
    //JoinTable生成一个中间表
    @JsonIgnore
    @ManyToMany(cascade = { CascadeType.PERSIST })
    @JoinTable(name = "follow",joinColumns = @JoinColumn(name = "followerID"),
            inverseJoinColumns = @JoinColumn(name = "focusID"))
    private List<WxAccount> followers;

    @JsonIgnore
    @ManyToMany(cascade = { CascadeType.PERSIST })
    @JoinTable(name = "follow",joinColumns = @JoinColumn(name = "focusID"),
            inverseJoinColumns = @JoinColumn(name = "followerID"))
    private List<WxAccount> focusers;

    @JsonIgnore
    @ManyToMany(cascade = {CascadeType.PERSIST})
    @JoinTable(name="supportarticle",joinColumns = @JoinColumn(name = "userid"),inverseJoinColumns = @JoinColumn(name = "supportarcticleid"))
    private List<Article> supportarticles;

    public List<Article> getSupportarticles() {
        return supportarticles;
    }

    @JsonBackReference
    public void setSupportarticles(List<Article> supportarticles) {
        this.supportarticles = supportarticles;
    }

    @JsonBackReference
    public void setArticleList(List<Article> articleList) {
        this.articleList = articleList;
    }

    @JsonBackReference
    public void setFollowers(List<WxAccount> followers) {
        this.followers = followers;
    }

    @JsonBackReference
    public void setFocusers(List<WxAccount> focusers) {
        this.focusers = focusers;
    }

    public List<Article> getArticleList() {
        return articleList;
    }

    public List<WxAccount> getFollowers() {
        return followers;
    }

    public List<WxAccount> getFocusers() {
        return focusers;
    }

    public List<Comment> getCommentList() {
        return commentList;
    }

    public void setCommentList(List<Comment> commentList) {
        this.commentList = commentList;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getWxOpenid() {
        return wxOpenid;
    }

    public void setWxOpenid(String wxOpenid) {
        this.wxOpenid = wxOpenid;
    }

    public String getSessionKey() {
        return sessionKey;
    }

    public void setSessionKey(String sessionKey) {
        this.sessionKey = sessionKey;
    }

    public Date getLastTime() {
        return lastTime;
    }

    public void setLastTime(Date lastTime) {
        this.lastTime = lastTime;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getNickname() {
        return nickname;
    }

    public void setwxUserInfo(wxUserInfo wxUserInfo){
        setAvatarUrl(wxUserInfo.getAvatarUrl());
        setNickname(wxUserInfo.getNickName());
        setGender(wxUserInfo.getGender());
        setCity(wxUserInfo.getCity());
        setProvince(wxUserInfo.getProvince());
        setCountry(wxUserInfo.getCountry());
        setLanguage(wxUserInfo.getLanguage());
    }
}
