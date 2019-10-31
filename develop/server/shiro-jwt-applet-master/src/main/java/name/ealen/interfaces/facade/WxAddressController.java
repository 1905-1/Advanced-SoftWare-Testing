package name.ealen.interfaces.facade;

import name.ealen.application.WxAppletService;
import name.ealen.domain.entity.Address;
import name.ealen.domain.service.WxAddressSevice;
import name.ealen.interfaces.dto.ResponseMsg;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 小程序后台 API
 */
@RestController
public class WxAddressController {
    @Resource
    private WxAddressSevice wxAddressSevice;

    /**
     * 地区
     * @return
     */
    @GetMapping("/api/wx/address/all")
    public ResponseEntity wxAddressGetAll() {
        return new ResponseEntity<>(new ResponseMsg(ResponseMsg.OK,wxAddressSevice.getAll(),""), HttpStatus.OK);
    }

    @RequiresAuthentication
    @PostMapping("/api/wx/address/addOne")
    public ResponseEntity wxaddOneAddress(@RequestBody Address address) {
        return new ResponseEntity<>(new ResponseMsg(ResponseMsg.OK,wxAddressSevice.add(address),""), HttpStatus.OK);
    }
}
