package cn.everythinggrows.boot.egboot.blog.model;

public class egUidArticle {
    private Long id;
    private Long uid;
    private Long aid;
    private String articleName;
    private String title;
    private String coverPic;
    private int type;
    private int createAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public Long getAid() {
        return aid;
    }

    public void setAid(Long aid) {
        this.aid = aid;
    }

    public String getArticleName() {
        return articleName;
    }

    public void setArticleName(String articleName) {
        this.articleName = articleName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCoverPic() {
        return coverPic;
    }

    public void setCoverPic(String coverPic) {
        this.coverPic = coverPic;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getCreateAt() {
        return createAt;
    }

    public void setCreateAt(int createAt) {
        this.createAt = createAt;
    }

    @Override
    public String toString() {
        return "egUidArticle{" +
                "id=" + id +
                ", uid=" + uid +
                ", aid=" + aid +
                ", articleName='" + articleName + '\'' +
                ", title='" + title + '\'' +
                ", coverPic='" + coverPic + '\'' +
                ", type=" + type +
                ", createAt=" + createAt +
                '}';
    }
}