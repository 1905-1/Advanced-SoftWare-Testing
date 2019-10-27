package name.ealen.interfaces.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import java.util.Date;

@Data
public class ArticleDTO {
    private int articleId;

    //文章名
    private String title;
    //发表时间
    private String date;

    //封面图片
    private String coverimg;
    //内容
    private String content;
    //点赞数
    private int supportnum;
    //游览时长
    private int playtime;
    //费用
    private int fee;

    private long hotnum;

    private int id;

    private String addressId;

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

    public void setDate(String date) {
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public String getCoverimg() {
        return coverimg;
    }

    public void setCoverimg(String coverimg) {
        this.coverimg = coverimg;
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

    public void setSupportnum(int supportnum) {
        this.supportnum = supportnum;
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

    public long getHotnum() {
        return hotnum;
    }

    public void setHotnum(long hotnum) {
        this.hotnum = hotnum;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAddressId() {
        return addressId;
    }

    public void setAddressId(String addressId) {
        this.addressId = addressId;
    }
}
