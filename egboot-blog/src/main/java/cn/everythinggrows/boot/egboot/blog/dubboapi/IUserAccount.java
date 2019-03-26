package cn.everythinggrows.boot.egboot.blog.dubboapi;


import cn.everythinggrows.boot.egboot.blog.model.egUser;

public interface IUserAccount {
    public String getMailVerifyAndSend(String toMail);

    public String ICreateUser(egUser user, String vertify);

    public String login(egUser user);

    public egUser getUser(long uid);
}

