package cn.everythinggrows.boot.egboot.forum.model;

import java.io.Serializable;

public class emailUid implements Serializable {

    private static final long serialVersionUID = 7472381898207056898L;

    private Long hashid;
    private String email;
    private Long uid;
    public Long getHashid() {
        return hashid;
    }

    public void setHashid(Long hashid) {
        this.hashid = hashid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }


}
