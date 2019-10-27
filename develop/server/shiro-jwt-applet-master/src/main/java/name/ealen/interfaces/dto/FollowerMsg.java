package name.ealen.interfaces.dto;

import name.ealen.domain.entity.WxAccount;

import java.util.List;

public class FollowerMsg {
    public List<WxAccount> followers;
    public boolean hasme = false;

    public List<WxAccount> getFollowers() {
        return followers;
    }

    public void setFollowers(List<WxAccount> followers) {
        this.followers = followers;
    }

    public boolean isHasme() {
        return hasme;
    }

    public void setHasme(boolean hasme) {
        this.hasme = hasme;
    }
}
