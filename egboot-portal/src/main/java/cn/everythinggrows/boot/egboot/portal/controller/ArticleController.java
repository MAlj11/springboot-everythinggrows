package cn.everythinggrows.boot.egboot.portal.controller;

import cn.everythinggrows.boot.egboot.portal.Utils.CookieUtils;
import cn.everythinggrows.boot.egboot.portal.Utils.EgResult;
import cn.everythinggrows.boot.egboot.portal.Utils.HttpClientUtil;
import cn.everythinggrows.boot.egboot.portal.Utils.HttpRequsetUtil;
import cn.everythinggrows.boot.egboot.portal.model.EgTypeArticle;
import cn.everythinggrows.boot.egboot.portal.model.UploadImage;
import cn.everythinggrows.boot.egboot.portal.model.egUidArticle;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.*;

@Controller
public class ArticleController {
    private Logger logger = LoggerFactory.getLogger(ArticleController.class);
    @Value("${BLOG_BASE_URL}")
    String BLOG_BASE_URL;

    @Value("${image_dns}")
    String image_dns;

    @RequestMapping(value = "/writearticle.html")
    public String writeArticle(HttpServletRequest request) throws UnsupportedEncodingException {
        String tokenVal = CookieUtils.getCookieValue(request, "eg_cookie_token");
        if (tokenVal == null || tokenVal.length() == 0) {
            return "lw-log";
        }
        String image =  CookieUtils.getCookieValue(request, "eg_upload_image");
        List<UploadImage> imageList = new ArrayList<>();
        if(image == null || image.length() == 0){
            imageList = new ArrayList<>();
        }else {
            image = URLDecoder.decode(image, "utf-8");
            String[] images = image.split(";");
            UploadImage uploadImage = new UploadImage();
            for(String url : images){
                uploadImage.setPicUrl(url);
                imageList.add(uploadImage);
            }
        }
        HttpSession session = request.getSession();
        session.setAttribute("imageList",imageList);
        return "lw-write";
    }

    @RequestMapping(value = "/blog/article/publish.html", method = RequestMethod.POST)
    public String writePublic(@RequestParam(value = "articleName") String articleName,
                              @RequestParam(value = "title") String title,
                              @RequestParam(value = "content") String content,
                              @RequestParam(value = "type") int type,
                              HttpServletRequest request,
                              HttpServletResponse response) throws UnsupportedEncodingException {
        HttpSession session = request.getSession();
        if(StringUtils.isEmpty(articleName) || StringUtils.isEmpty(title) || StringUtils.isEmpty(content)
                || type == 0){
            session.setAttribute("publishError", "文章信息不能为空");
            return "publishisnull";
        }
        String insertArtUrl = BLOG_BASE_URL + "/article/insert";
        Map<String, String> param = new HashMap<>();
        param.put("articleName", articleName);
        param.put("title", title);
        param.put("type", String.valueOf(type));
        param.put("content", content);
        String tokenVal = CookieUtils.getCookieValue(request, "eg_cookie_token");
        tokenVal = URLDecoder.decode(tokenVal, "utf-8");
        CookieUtils.deleteCookie(request,response,"eg_upload_image");
        String ret = HttpClientUtil.doPostWithHander(insertArtUrl, param, "x-eg-session", tokenVal);
        JSONObject json = JSON.parseObject(ret);
        Map dataMap = JSONObject.toJavaObject(json, Map.class);
        if ((Integer) dataMap.get("status") == 200) {
            return "publishSuccess";
        }
        return "lw-write";
    }

    @RequestMapping("/upload/delete")
    public EgResult deleteImage(@RequestParam("picUrl") String picUrl,
                              HttpServletRequest request) throws UnsupportedEncodingException {
        String[] urlline = picUrl.split("/");
        int length = urlline.length;
        int last = length-1;
        String url = urlline[last];
        String image =  CookieUtils.getCookieValue(request, "eg_upload_image");
        List<UploadImage> imageList = new ArrayList<>();
        if(image == null || image.length() == 0){
            return EgResult.systemError();
        }else {
            image = URLDecoder.decode(image, "utf-8");
            String[] images = image.split(";");
            for(String urltrue : images){
            }
        }
        return EgResult.ok();
    }

    //todo 前端增加type字段的选项


    @RequestMapping(value = "/myArticle.html")
    public String getMyArticle(HttpServletRequest request) throws UnsupportedEncodingException {
        String tokenVal = CookieUtils.getCookieValue(request, "eg_cookie_token");
        if (tokenVal == null || tokenVal.length() == 0) {
            return "lw-log";
        }
        tokenVal = URLDecoder.decode(tokenVal, "utf-8");
        HttpSession session = request.getSession();
        long uid = getUid(tokenVal);
        String myArtUrl = BLOG_BASE_URL + "/article/myarticle/" + String.valueOf(uid);
        JSONObject myartJson = HttpRequsetUtil.requestGet(myArtUrl, null);
        String myartList = myartJson.getString("egUidArticles");
        List<egUidArticle> egUidArticleList = JSONObject.parseArray(myartList, egUidArticle.class);
        logger.info("myarticle:{}", egUidArticleList);
        session.setAttribute("myArticles", egUidArticleList);
        return "lw-myArticle";
    }

    @RequestMapping("/delete/article/{aid}")
    public String deleteArticle(@PathVariable(value = "aid") long aid,
                                HttpServletRequest request) throws UnsupportedEncodingException {
        String tokenVal = CookieUtils.getCookieValue(request, "eg_cookie_token");
        if (tokenVal == null || tokenVal.length() == 0) {
            return "lw-log";
        }
        tokenVal = URLDecoder.decode(tokenVal, "utf-8");
        String delUrl = BLOG_BASE_URL + "/article/delete/" + String.valueOf(aid);
        String ret = HttpClientUtil.doGetWithHander(delUrl, null, "x-eg-session", tokenVal);
        JSONObject json = JSON.parseObject(ret);
        Map dataMap = JSONObject.toJavaObject(json, Map.class);
        if ((Integer) dataMap.get("status") == 200) {
            return "delSuccess";
        }
        return "lw-myArticle";
    }

    @RequestMapping("/fileUpload")
    public String articlePic(@RequestParam(value = "file") MultipartFile file,
                             Model model,
                             HttpServletRequest request,
                             HttpServletResponse response) throws UnsupportedEncodingException {
        HttpSession session = request.getSession();
        if (file.isEmpty()) {
            System.out.println("file if empty");
        }
        String fileName = file.getOriginalFilename();
        // 文件名
        String suffixName = fileName.substring(fileName.lastIndexOf("."));
        // 后缀名
        String filePath = "C:/tomcat_image/apache-tomcat-8.5.37/webapps/ROOT/articleImages/";
        // 上传后的路径
        fileName = UUID.randomUUID() + suffixName;
        // 新文件名
        File dest = new File(filePath + fileName);
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
        }
        try {
            file.transferTo(dest);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String filename = image_dns + fileName;
        String imageList = CookieUtils.getCookieValue(request, "eg_upload_image");
        String image = null;
        if(imageList == null || imageList.length() == 0){
            image = filename;
        }else{
            image = URLDecoder.decode(imageList, "utf-8");
            image = image + ";" + filename;
        }
        //将token存入浏览器的cookie中
        Cookie cookie = new Cookie("eg_upload_image", URLEncoder.encode(image, "utf-8"));
        response.addCookie(cookie);
        return "lw-write";
    }

    public long getUid(String session) {
        if (session == null || session.length() == 0) {
            return 0;
        }
        String[] line = session.split(";");
        long uid = Long.parseLong(line[0]);
        return uid;
    }
}
