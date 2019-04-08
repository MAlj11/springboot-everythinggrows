package cn.everythinggrows.boot.egboot.admin.model;

import java.io.Serializable;

public class EgTypeArticle implements Serializable {
    private Long id;
    private Integer type;
    private String articleName;
    private String coverPic;
    private Long aid;
    private Long uid;
    private String title;
    private String typeString;


    public String getTypeString() {
        return typeString;
    }

    public void setTypeString(String typeString) {
        this.typeString = typeString;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getArticleName() {
        return articleName;
    }

    public void setArticleName(String articleName) {
        this.articleName = articleName;
    }

    public String getCoverPic() {
        return coverPic;
    }

    public void setCoverPic(String coverPic) {
        this.coverPic = coverPic;
    }

    public Long getAid() {
        return aid;
    }

    public void setAid(Long aid) {
        this.aid = aid;
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    @Override
    public String toString() {
        return "EgTypeArticle{" +
                "id=" + id +
                ", type=" + type +
                ", articleName='" + articleName + '\'' +
                ", coverPic='" + coverPic + '\'' +
                ", aid=" + aid +
                ", uid=" + uid +
                ", title='" + title + '\'' +
                ", typeString='" + typeString + '\'' +
                '}';
    }
}
