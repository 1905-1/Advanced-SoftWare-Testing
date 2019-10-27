package name.ealen.interfaces.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 */
@Data
public class CodeDTO {
    @NotEmpty(message = "缺少参数code或code不合法")
    private String code;

//    @NotEmpty(message = "缺少参数userInfo")
    private wxUserInfo userInfo;

    public wxUserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(wxUserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
