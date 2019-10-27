package name.ealen.interfaces.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * DTO 返回值token对象
 */
@Data
@AllArgsConstructor
public class TokenDTO {
    private String token;


    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
