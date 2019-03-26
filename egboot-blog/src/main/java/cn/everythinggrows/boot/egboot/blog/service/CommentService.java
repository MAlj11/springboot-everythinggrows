package cn.everythinggrows.boot.egboot.blog.service;

import cn.everythinggrows.boot.egboot.blog.Utils.ArticleUtils;
import cn.everythinggrows.boot.egboot.blog.dao.CommentDao;
import cn.everythinggrows.boot.egboot.blog.dubboapi.IUserAccount;
import cn.everythinggrows.boot.egboot.blog.model.Comment;
import cn.everythinggrows.boot.egboot.blog.model.egUser;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {

    @Autowired
    private CommentDao commentDao;
    @Reference(version = "1.0.0")
    private IUserAccount iUserAccount;


    public List<Comment> getCommentWithAid(long aid){
        List<Comment> comments = commentDao.getCommentList(aid);
        return comments;
    }

    public int insertComment(long aid, long uid,String content){
        egUser user = iUserAccount.getUser(uid);
        Comment comment = new Comment();
        long cid = ArticleUtils.getCommentId();
        comment.setCid(cid);
        comment.setAid(aid);
        comment.setContent(content);
        comment.setUid(uid);
        comment.setPortrait(user.getPortrait());
        comment.setUsername(user.getUsername());
        int i = commentDao.insertComment(comment);
        return i;
    }

}
