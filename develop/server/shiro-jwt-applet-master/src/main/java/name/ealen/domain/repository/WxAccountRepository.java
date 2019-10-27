package name.ealen.domain.repository;

import name.ealen.domain.entity.WxAccount;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 */
public interface WxAccountRepository extends JpaRepository<WxAccount, Integer> {

    /**
     * 根据OpenId查询用户信息
     */
    WxAccount findByWxOpenid(String wxOpenId);
    /**
     * 根据id查询用户信息
     */
    WxAccount findById(int id);
}
