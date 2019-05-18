package cn.everythinggrows.boot.egboot.blog.service;

import cn.everythinggrows.boot.egboot.blog.dao.CommentDao;
import cn.everythinggrows.boot.egboot.blog.model.Comment;
import cn.everythinggrows.boot.egboot.blog.model.egUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {

    @Autowired
    private CommentDao commentDao;
    @Autowired
    private RedisClientTemplate redisClientTemplate;
    @Autowired
    private HttpRequestToUser httpRequestToUser;

    @Value("${portrait_dns}")
    String portraicdns;

    public List<Comment> getCommentWithAid(long aid) {
        List<Comment> comments = commentDao.getCommentList(aid);
        for (Comment comment : comments) {
            String portraitTrue = portraicdns + comment.getPortrait();
            comment.setPortrait(portraitTrue);
        }
        return comments;
    }

    public int insertComment(long aid, long uid, String content) {
        egUser user = httpRequestToUser.getUserWithCloud(uid);
        Comment comment = new Comment();
        long cid = redisClientTemplate.cidGeneration();
        comment.setCid(cid);
        comment.setAid(aid);
        comment.setContent(content);
        comment.setUid(uid);
        //http://localhost:8080/static/uploadImages/portrait/13.jpg

        String portrairDns = user.getPortrait();
        String[] line = portrairDns.split("/");
        int lastIndex = line.length - 1;
        String portrait = line[lastIndex];
        comment.setPortrait(portrait);
        comment.setUsername(user.getUsername());
        int i = commentDao.insertComment(comment);
        return i;
    }

    public int deleteComment(long cid, long aid) {
        return commentDao.deleteComment(cid, aid);
    }

}
