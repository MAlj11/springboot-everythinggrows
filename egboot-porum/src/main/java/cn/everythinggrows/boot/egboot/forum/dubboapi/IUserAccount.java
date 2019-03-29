package cn.everythinggrows.boot.egboot.forum.dubboapi;


import cn.everythinggrows.boot.egboot.forum.model.egUser;

public interface IUserAccount {
    public String getMailVerifyAndSend(String toMail);

    public String ICreateUser(egUser user, String vertify);

    public String login(egUser user);

    public egUser getUser(long uid);

    public String dubbotest(String str);
}

