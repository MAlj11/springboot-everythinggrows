package cn.everythinggrows.boot.egboot.blog.Utils;

import cn.everythinggrows.boot.egboot.blog.service.RedisClientTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

public class SerializeUtil {
    private static final Logger log=LoggerFactory.getLogger(SerializeUtil.class);
    // 序列化
    public static byte[] serialize(Object object) {

        ObjectOutputStream oos = null;
        ByteArrayOutputStream baos = null;
        byte[] bytes = null;
        try {
            baos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(baos);
            oos.writeObject(object);
            bytes = baos.toByteArray();
        } catch (Exception e) {
           log.error("{}---------------序列化失败",e.getMessage());
        }
        return bytes;
    }

    // 反序列化
    public static Object deserialize(byte[] bytes) throws IOException, ClassNotFoundException {

        ByteArrayInputStream bais = null;
        ObjectInputStream ois = null;
        try {
            bais = new ByteArrayInputStream(bytes);
            ois = new ObjectInputStream(bais);
        } catch (Exception e) {
            log.error("{}---------------反列化失败",e.getMessage());
        }
        return ois.readObject();
    }
}
