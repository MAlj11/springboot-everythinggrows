package cn.everythinggrows.boot.egboot.blog.Utils;

public class DBUtils {

    public static int getDBKey(long uid){
        if(uid == 0){
            return 0;
        }
        int ret = (int)uid % 8;
        return ret;
    }

    public static int getTableKey(long uid){
        if(uid == 0){
            return 0;
        }
        int ret = (int)uid % 32;
        return ret;
    }
}
