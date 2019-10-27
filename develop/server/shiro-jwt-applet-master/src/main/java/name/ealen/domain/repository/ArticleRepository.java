package name.ealen.domain.repository;

import name.ealen.domain.entity.Article;
import name.ealen.domain.entity.*;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ArticleRepository extends CrudRepository<Article, Integer> {
    /**
     * 通过文章ID获得文章
     * @param articleId
     * @return
     */
    Article findByArticleId (int articleId);

    @Query("select a from Article a join a.address d on a.address.addressId = d.addressId where d.desc like CONCAT('%',:name,'%')")
    Page<Article> findByAddressLike (@Param("name") String name, Pageable pageable);

    List<Article> findByAddress_AddressId(String id);

    List<Article> findByWxAccount_Id(int id);

    //获取热门的十个
    Page<Article> findAll(Pageable pageable);

    //获取该地点热门的前x个
    Page<Article> findByAddress_AddressIdOrderByHotnum(String id,Pageable pageable);

    //获取该用户点赞醉的文章
    List<Article> findByWxAccount_IdOrderByHotnumDesc(int id);
}
