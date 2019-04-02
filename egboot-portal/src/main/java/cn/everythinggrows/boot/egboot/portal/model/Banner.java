package cn.everythinggrows.boot.egboot.portal.model;

import java.io.Serializable;

public class Banner implements Serializable {
    private Integer id;
    private String bannerPic;
    private String bannerTitle;
    private String bannerUrl;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBannerPic() {
        return bannerPic;
    }

    public void setBannerPic(String bannerPic) {
        this.bannerPic = bannerPic;
    }

    public String getBannerTitle() {
        return bannerTitle;
    }

    public void setBannerTitle(String bannerTitle) {
        this.bannerTitle = bannerTitle;
    }

    public String getBannerUrl() {
        return bannerUrl;
    }

    public void setBannerUrl(String bannerUrl) {
        this.bannerUrl = bannerUrl;
    }

    @Override
    public String toString() {
        return "Banner{" +
                "id=" + id +
                ", bannerPic='" + bannerPic + '\'' +
                ", bannerTitle='" + bannerTitle + '\'' +
                ", bannerUrl='" + bannerUrl + '\'' +
                '}';
    }
}
