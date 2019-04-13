package cn.everythinggrows.cn.eg.search.entity;


import io.searchbox.annotations.JestId;
import org.springframework.data.elasticsearch.annotations.Document;

import java.io.Serializable;

@Document(indexName = "egsearch", type = "article")
public class Article implements Serializable {
    private static final long serialVersionUID = -763638353551774166L;

    public static final String INDEX_NAME = "egsearch";

    public static final String TYPE = "article";

    @JestId
    private Long id;
    private String title;
    private String auther;
    private String createAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuther() {
        return auther;
    }

    public void setAuther(String auther) {
        this.auther = auther;
    }

    public String getCreateAt() {
        return createAt;
    }

    public void setCreateAt(String createAt) {
        this.createAt = createAt;
    }

    public Article(Long id, String title, String auther, String createAt) {
        this.id = id;
        this.title = title;
        this.auther = auther;
        this.createAt = createAt;
    }
}
