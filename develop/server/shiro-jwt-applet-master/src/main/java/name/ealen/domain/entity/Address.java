package name.ealen.domain.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Table
@Data
public class Address {
    @Id
    @Column(name = "addressId")
    private String addressId;
    //描述地点名称
    @Column(name = "addressName")
    private String desc;
    //游览时长
    @Column(name = "playtime")
    private int playtime;
    //游览费用
    @Column(name = "fee")
    private int fee;

    //一个地方的旅游攻略有多个
    @OneToMany(mappedBy = "address",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<Article> articleList; //文章列表

    public String getAddressId() {
        return addressId;
    }

    public void setAddressId(String addressId) {
        this.addressId = addressId;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
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

    public List<Article> getArticleList() {
        return articleList;
    }

    public void setArticleList(List<Article> articleList) {
        this.articleList = articleList;
    }
}
