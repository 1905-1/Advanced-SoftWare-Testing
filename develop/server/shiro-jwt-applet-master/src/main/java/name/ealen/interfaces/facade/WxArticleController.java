package name.ealen.interfaces.facade;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import name.ealen.application.ArticleService;
import name.ealen.domain.entity.Article;
import name.ealen.domain.service.WxArticleService;
import name.ealen.infrastructure.util.UploadUtils;
import name.ealen.interfaces.dto.*;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 小程序获取 API
 */
@RestController
public class WxArticleController {

    @Resource
    private WxArticleService articleService;

    /**
     * 保存文章
     * @param article
     * @return
     */
    @RequiresAuthentication
    @PostMapping("/api/wx/article/add")
    public ResponseEntity wxArticleAdd(ArticleDTO article, @RequestParam("img")MultipartFile imgFile, HttpServletRequest request, HttpSession session) {
        if(imgFile.isEmpty()){
            return new ResponseEntity<>("未上传封面图片",HttpStatus.BAD_REQUEST);
        }

        String filename = imgFile.getOriginalFilename();

        File fileDir = UploadUtils.getImgDirFile();
        File storgefile = new File(fileDir.getAbsolutePath()+"/"+filename);
        try{
            imgFile.transferTo(storgefile);
        }catch (IOException e){
            e.printStackTrace();
            return new ResponseEntity<>("保存文件失败",HttpStatus.BAD_REQUEST);

        }
        article.setCoverimg("/upload/imgs/"+filename);
        Article result = articleService.addArticle(article);

        if(result == null){
            return new ResponseEntity<>(new ResponseMsg(ResponseMsg.EMPTY,null,"add article failed"), HttpStatus.OK);
        }
        return new ResponseEntity<>(new ResponseMsg(ResponseMsg.OK,result,"add article success"), HttpStatus.OK);
    }

    /**
     * 热搜文章
     * @return
     */
    @GetMapping("/api/wx/article/allhot")
    public ResponseMsg wxArticleGetHot(HttpServletRequest request,@RequestParam int lookUserId) {
        return new ResponseMsg(ResponseMsg.OK,articleService.getHotArticle(lookUserId),"");
    }

    /**
     * 热搜文章分页查询
     * @param page
     * @param size
     * @return
     */
    @GetMapping("/api/wx/article/hot")
    public ResponseMsg wxArticleGetHotByPage(@RequestParam int page,@RequestParam int size,@RequestParam int lookUserId,HttpServletRequest request) {
        return new ResponseMsg(ResponseMsg.OK,articleService.getHotArticle(page,size,lookUserId),"");
    }

    /**
     * 获得某个用户的所有文章
     * @param uid
     * @return
     */
    @GetMapping("/api/wx/article/user")
    public ResponseMsg wxArticleGetByUser(@RequestParam int uid,@RequestParam int lookUserId,HttpServletRequest request){
        return new ResponseMsg(ResponseMsg.OK,articleService.getArticleByUserId(uid,lookUserId),"");
    }

    /**
     * 获得某个用户所有文章按热度排名
     * @param uid
     * @return
     */
    @GetMapping("/api/wx/article/userhot")
    public ResponseMsg wxArticleGetHotByUser(@RequestParam int uid,@RequestParam int lookUserId,HttpServletRequest request){
        return new ResponseMsg(ResponseMsg.OK,articleService.getHotArticleByUserId(uid,lookUserId),"");
    }

    /**
     * 获得某个地点的旅游攻略
     * @param address
     * @return
     */
    @GetMapping("/api/wx/article/address")
    public ResponseMsg wxArticleGetHotByAddress(@RequestParam String address,@RequestParam int lookUserId,HttpServletRequest request){
        return new ResponseMsg(ResponseMsg.OK,articleService.getArticleByAddress(address,lookUserId),"");
    }

    /**
     * 获得某个地点的旅游攻略 分页查询
     * @param address
     * @param page
     * @param size
     * @return
     */
    @GetMapping("/api/wx/article/addresshot")
    public ResponseMsg wxArticleGetHotByAddress(@RequestParam String address,@RequestParam int page,@RequestParam int size,@RequestParam int lookUserId,HttpServletRequest request){
        return new ResponseMsg(ResponseMsg.OK,articleService.getHotArticleByAddress(address,page,size,lookUserId),"");
    }

    //模糊查询
    @GetMapping("/api/wx/article/like/address")
    public ResponseMsg wxArticleGetHotAddressLike(@RequestParam String likename,@RequestParam int page,@RequestParam int size,@RequestParam int lookUserId,HttpServletRequest request){
        return new ResponseMsg(ResponseMsg.OK,articleService.getArticleAddressLike(likename,page,size,lookUserId),"");
    }

    //文章点赞
    @RequiresAuthentication
    @GetMapping("/api/wx/article/add/support")
    public ResponseMsg wxArticleSupport(@RequestParam int id,@RequestParam int articleid,HttpServletRequest request){
        return new ResponseMsg(ResponseMsg.OK,articleService.addSupportUser(id,articleid),"");
    }
    //取消文章点赞
    @RequiresAuthentication
    @GetMapping("/api/wx/article/delete/support")
    public ResponseMsg wxArticleDeleteSupport(@RequestParam int id,@RequestParam int articleid,HttpServletRequest request){
        return new ResponseMsg(ResponseMsg.OK,articleService.removeSupportUser(id,articleid),"");
    }

    //文章热度
    @GetMapping("/api/wx/article/view")
    public ResponseMsg wxArticleHotnum(@RequestParam int articleid,HttpServletRequest request){
        return new ResponseMsg(ResponseMsg.OK,articleService.addHotnum(articleid),"");
    }
}
