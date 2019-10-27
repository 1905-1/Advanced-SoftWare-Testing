package name.ealen.interfaces.facade;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import name.ealen.application.WxAppletService;
import name.ealen.domain.entity.WxAccount;
import name.ealen.domain.service.WxAccountService;
import name.ealen.interfaces.dto.CodeDTO;
import name.ealen.interfaces.dto.ResponseMsg;
import name.ealen.interfaces.dto.TokenDTO;
import name.ealen.interfaces.dto.wxUserInfo;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 小程序后台 API
 */
@RestController
public class WxAppletController {

    @Resource
    private WxAccountService wxAppletService;

    /**
     * 微信小程序端用户登陆api
     * 返回给小程序端 自定义登陆态 token
     */
    @PostMapping("/api/wx/user/login")
    public ResponseEntity wxAppletLoginApi(@RequestBody @Validated CodeDTO requestMap) {
        TokenDTO code =  wxAppletService.wxUserLogin(requestMap.getCode(),requestMap.getUserInfo());
        WxAccount account = wxAppletService.getUserBytoken(code.getToken());
        JSONObject result = new JSONObject();
        result.put("token",code.getToken());
        result.put("account",account);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("/api/wx/user/userinfo")
    public ResponseEntity wxAppletGetUserInfo(HttpServletRequest request){
        return new ResponseEntity<>(wxAppletService.getUserBytoken(request.getHeader("Authorization")), HttpStatus.OK);
    }


    /**
     * 需要认证的测试接口  需要 @RequiresAuthentication 注解，则调用此接口需要 header 中携带自定义登陆态 authorization
     */
    @RequiresAuthentication
    @PostMapping("/api/wx/sayHello")
    public ResponseEntity sayHello() {
        Map<String, String> result = new HashMap<>();
        result.put("words", "hello World");
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @RequiresAuthentication
    @GetMapping("/api/wx/add/follow")
    public ResponseEntity addfollow(int id,int followid){
        return new ResponseEntity<>(wxAppletService.addFollow(id,followid),HttpStatus.OK);
    }

    @RequiresAuthentication
    @DeleteMapping("/api/wx/delete/follow")
    public ResponseEntity removefollow(int id,int followid){
        return new ResponseEntity<>(wxAppletService.removerFollow(id,followid),HttpStatus.OK);
    }

    @RequiresAuthentication
    @GetMapping("/api/wx/get/follow")
    public ResponseMsg getAllfollow(int id,HttpServletRequest request){
        List<WxAccount> result = wxAppletService.GetAllFollower(id);
        WxAccount me = wxAppletService.getUserBytoken(request.getHeader("Authorization"));
        boolean hasme = result.contains(me);

        return new ResponseMsg(1,result,hasme);
    }
}
