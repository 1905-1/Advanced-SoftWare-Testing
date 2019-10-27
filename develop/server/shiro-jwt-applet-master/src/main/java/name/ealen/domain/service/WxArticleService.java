package name.ealen.domain.service;

import name.ealen.application.ArticleService;
import name.ealen.domain.entity.Address;
import name.ealen.domain.entity.Article;
import name.ealen.domain.entity.WxAccount;
import name.ealen.domain.repository.ArticleRepository;
import name.ealen.domain.repository.WxAccountRepository;
import name.ealen.domain.vo.JwtToken;
import name.ealen.interfaces.dto.ArticleDTO;
import name.ealen.interfaces.dto.ArticleResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * 实体 行为描述
 */
@Service
@Transactional
public class WxArticleService implements ArticleService {


    @Resource
    private ArticleRepository articleRepository;
    @Resource
    private WxAccountRepository wxAccountRepository;
    /**
     * 选择前十的热门文章
     * @return
     */
    @Override
    public List<ArticleResponse> getHotArticle(int lookUserId) {
        int page = 0,size = 10;
        return getHotArticle(page,size,lookUserId);
    }

    public List<ArticleResponse> proccessArticle(List<Article> result,int lookUserId){
        WxAccount me = wxAccountRepository.findById(lookUserId);
        Iterator<Article> iterator = result.iterator();
        List<ArticleResponse> responses = new ArrayList<>();
        while (iterator.hasNext()){
            Article article = iterator.next();
            ArticleResponse articleResponse = new ArticleResponse(article);
            articleResponse.setIssupport(article.getSupportusers().contains(me));
            responses.add(articleResponse);
        }

        return responses;
    }


    public List<ArticleResponse> getHotArticle(int page,int size,int lookUserId){
        Sort sort = new Sort(Sort.Direction.DESC,"hotnum");
        Pageable pageable = PageRequest.of(page,size,sort);

        List<Article> result = articleRepository.findAll(pageable).getContent();

        return proccessArticle(result,lookUserId);
    }

    @Override
    public List<ArticleResponse> getArticleByAddress(String addressId,int lookUserId) {

        List<Article> result = articleRepository.findByAddress_AddressId(addressId);
        return  proccessArticle(result,lookUserId);
    }

    public List<ArticleResponse> getHotArticleByAddress(String addressId,int page,int size,int lookUserId) {
        Sort sort = new Sort(Sort.Direction.DESC,"hotnum");
        Pageable pageable = PageRequest.of(page,size,sort);
        List<Article> result = articleRepository.findByAddress_AddressIdOrderByHotnum(addressId,pageable).getContent();

        return proccessArticle(result,lookUserId);
    }

    @Override
    public Article getArticleById(int id,int lookUserId) {
        return articleRepository.findByArticleId(id);
    }

    @Override
    public List<ArticleResponse> getArticleByUserId(int id,int lookUserId) {
        List<Article> result = articleRepository.findByWxAccount_Id(id);
        return proccessArticle(result,lookUserId);
    }

    @Override
    public Article addArticle(ArticleDTO article) {
        Article result = null;
        Article input = new Article();
        input.setCoverimg(article.getCoverimg());
        Address address = new Address();
        address.setAddressId(article.getAddressId());
        input.setAddress(address);
        input.setContent(article.getContent());
        Date date = new Date();
        input.setDate(date);
        input.setFee(article.getFee());
        input.setHotnum(article.getHotnum());
        input.setTitle(article.getTitle());
        input.setPlaytime(article.getPlaytime());
        input.setSupportnum(article.getSupportnum());
        WxAccount account = new WxAccount();
        account.setId(article.getId());
        input.setWxAccount(account);
        result = articleRepository.save(input);
        return result;
    }

    public List<ArticleResponse> getHotArticleByUserId(int id,int lookUserId) {
        List<Article> result = articleRepository.findByWxAccount_IdOrderByHotnumDesc(id);
        return proccessArticle(result,lookUserId);
    }


    public List<ArticleResponse> getArticleAddressLike(String AddressName,int page,int size,int lookUserId) {
        Sort sort = new Sort(Sort.Direction.DESC,"hotnum");
        Pageable pageable = PageRequest.of(page,size,sort);
        List<Article> result = articleRepository.findByAddressLike(AddressName,pageable).getContent();
        return proccessArticle(result,lookUserId);
    }

    public List<ArticleResponse> getArticleByAddressId(String id,int lookUserId){
        List<Article> result = articleRepository.findByAddress_AddressId(id);
        return proccessArticle(result,lookUserId);
    }


    //点赞
    public int addSupportUser(int id,int articleid){
        Article article = articleRepository.findByArticleId(articleid);
        WxAccount wxAccount = wxAccountRepository.findById(id);
        if(!article.getSupportusers().contains(wxAccount)){
            article.getSupportusers().add(wxAccount);
            article.setSupportnum(article.getSupportusers().size());
        }
        articleRepository.save(article);
        return article.getSupportnum();
    }


    //取消赞
    public int removeSupportUser(int id,int articleid){
        Article article = articleRepository.findByArticleId(articleid);
        WxAccount wxAccount = wxAccountRepository.findById(id);
        if(!article.getSupportusers().contains(wxAccount)){
            article.getSupportusers().remove(wxAccount);
            article.setSupportnum(article.getSupportusers().size());
        }
        articleRepository.save(article);
        return article.getSupportnum();
    }


    //浏览增加热度 +1
    public long addHotnum(int articleid){
        Article article = articleRepository.findByArticleId(articleid);
        article.setHotnum(article.getHotnum()+1);
        articleRepository.save(article);
        return article.getHotnum();
    }
//    public Article UpdataSupport(int article){
//
//    }
}
