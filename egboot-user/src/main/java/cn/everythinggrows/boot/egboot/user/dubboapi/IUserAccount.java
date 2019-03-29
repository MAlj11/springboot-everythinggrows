package cn.everythinggrows.boot.egboot.user.dubboapi;


import cn.everythinggrows.boot.egboot.user.model.egUser;
import cn.everythinggrows.boot.egboot.user.utils.EgResult;

public interface IUserAccount {
    public String getMailVerifyAndSend(String toMail);

    public EgResult ICreateUser(egUser user, String vertify);

    public String login(egUser user);

    public egUser getUser(long uid);

    public String dubbotest(String str);
}

