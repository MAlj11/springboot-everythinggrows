package cn.everythinggrows.boot.egboot.user.dubboapi;


import cn.everythinggrows.boot.egboot.user.model.egUser;

public interface IUserAccount {
    public String getMailVerifyAndSend(String toMail);

    public String ICreateUser(egUser user, String vertify);

    public String login(egUser user);

    public egUser getUser(long uid);
}

