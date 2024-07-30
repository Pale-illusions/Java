package common;

/*
    表示一个用户信息
 */

import java.io.Serializable;

public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    private String userId; // 用户id
    private String pwd; // 密码

    public User(String userId, String pwd) {
        this.userId = userId;
        this.pwd = pwd;
    }

    public User() {
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }
}
