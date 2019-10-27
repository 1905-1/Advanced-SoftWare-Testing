package name.ealen.domain.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * 实体 属性描述 文章攻略
 */
@Entity
@Table
@Data
public class Article {
    @Id
    @GeneratedValue
    private int articleId;

    //文章名
    @Column(nullable = false,length = 50)
    private String title;
    //发表时间
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date date;

    //封面图片
    @Column(nullable = false,name = "coverimg")
    private String coverimg;
    //内容
    private String content;
    //点赞数
    @Column(name = "supportnum")
    private int supportnum;
    //游览时长
    private int playtime;
    //费用
    private int fee;

    @Column(name = "hotnum")
    private long hotnum;

    public long getHotnum() {
        return hotnum;
    }

    public void setHotnum(long hotnum) {
        this.hotnum = hotnum;
    }

    //一对多 一篇文章对应一个用户
    //可选属性optional=false,表示author不能为空。删除文章，不影响用户
    @ManyToOne(cascade = {CascadeType.MERGE,CascadeType.REFRESH},optional = false)
    @JoinColumn(name = "id")
    private WxAccount wxAccount;

    @ManyToOne(cascade = {CascadeType.MERGE,CascadeType.REFRESH},optional = false)
    @JoinColumn(name = "addressId")
    private Address address;

    @OneToMany(mappedBy = "article",cascade=CascadeType.ALL,fetch=FetchType.EAGER)
    public List<Comment> commentList;

    @JsonIgnore
    @ManyToMany(cascade = {CascadeType.PERSIST},fetch = FetchType.LAZY)
    @JoinTable(name="supportarticle",joinColumns = @JoinColumn(name = "supportarcticleid"),inverseJoinColumns = @JoinColumn(name = "userid"))
    private List<WxAccount> supportusers;

    public List<Comment> getCommentList() {
        return commentList;
    }

    @JsonBackReference
    public void setCommentList(List<Comment> commentList) {
        this.commentList = commentList;
    }

    public List<WxAccount> getSupportusers() {
        return supportusers;
    }

    @JsonBackReference
    public void setSupportusers(List<WxAccount> supportusers) {
        this.supportusers = supportusers;
    }

    public int getArticleId() {
        return articleId;
    }

    public void setArticleId(int articleId) {
        this.articleId = articleId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getCoverimg() {
        return coverimg;
    }

    public void setCoverimg(String cover_img) {
        this.coverimg = cover_img;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getSupportnum() {
        return supportnum;
    }

    public void setSupportnum(int support_num) {
        this.supportnum = support_num;
    }

    public int getPlaytime() {
        return playtime;
    }

    public void setPlaytime(int playtime) {
        this.playtime = playtime;
    }

    public int getFee() {
        return fee;
    }

    public void setFee(int fee) {
        this.fee = fee;
    }

    public WxAccount getWxAccount() {
        return wxAccount;
    }

    @JsonBackReference
    public void setWxAccount(WxAccount wxAccount) {
        this.wxAccount = wxAccount;
    }

    public Address getAddress() {
        return address;
    }

    @JsonBackReference
    public void setAddress(Address address) {
        this.address = address;
    }
}
