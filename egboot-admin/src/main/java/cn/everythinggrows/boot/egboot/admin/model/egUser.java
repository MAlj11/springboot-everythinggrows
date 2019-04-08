package cn.everythinggrows.boot.egboot.admin.model;

import java.io.Serializable;

public class egUser implements Serializable {

    private static final long serialVersionUID = 2597615064079410591L;


    private Long uid;
    private String username;
    private String password;
    private String email;
    private String portrait;
    private String createAt;
    private String extend;

    public String getCreateAt() {
        return createAt;
    }

    public void setCreateAt(String createAt) {
        this.createAt = createAt;
    }

    public String getExtend() {
        return extend;
    }

    public void setExtend(String extend) {
        this.extend = extend;
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPortrait() {
        return portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }

    @Override
    public String toString() {
        return "egUser{" +
                "uid=" + uid +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", portrait='" + portrait + '\'' +
                ", createAt='" + createAt + '\'' +
                ", extend='" + extend + '\'' +
                '}';
    }
}
