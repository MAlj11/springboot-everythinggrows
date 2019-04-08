package cn.everythinggrows.boot.egboot.admin.model;

public class TopicDetail {
    private Long id;
    private Long tid;
    private String content;
    private Long toUid;
    private String toUsername;
    private String toPortrait;
    private Long uid;
    private String username;
    private String portrait;
    private String extend;
    private Boolean isMe;
    private Integer createAt;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTid() {
        return tid;
    }

    public void setTid(Long tid) {
        this.tid = tid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getToUid() {
        return toUid;
    }

    public void setToUid(Long toUid) {
        this.toUid = toUid;
    }

    public String getToUsername() {
        return toUsername;
    }

    public void setToUsername(String toUsername) {
        this.toUsername = toUsername;
    }

    public String getToPortrait() {
        return toPortrait;
    }

    public void setToPortrait(String toPortrait) {
        this.toPortrait = toPortrait;
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

    public String getPortrait() {
        return portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }

    public String getExtend() {
        return extend;
    }

    public void setExtend(String extend) {
        this.extend = extend;
    }

    public Integer getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Integer createAt) {
        this.createAt = createAt;
    }

    public Boolean getMe() {
        return isMe;
    }

    public void setMe(Boolean me) {
        isMe = me;
    }
}
