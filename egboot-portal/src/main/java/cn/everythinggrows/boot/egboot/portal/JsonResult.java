package cn.everythinggrows.boot.egboot.portal;

import com.alibaba.fastjson.JSON;

import java.util.HashMap;
import java.util.Map;

public class JsonResult {
    private int code;
    //返回码 非0即失败
    private String msg;
    //消息提示
    private Map<String, Object> data;
    //返回的数据

    public JsonResult() {
    }

    ;

    public JsonResult(int code, String msg, Map<String, Object> data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static String success() {
        return success(new HashMap<>(0));
    }

    public static String success(Map<String, Object> data) {
        return JSON.toJSONString(new JsonResult(0, "success", data));
    }

    public static String failed() {
        return failed("failed");
    }

    public static String failed(String msg) {
        return failed(-1, msg);
    }

    public static String failed(int code, String msg) {
        return JSON.toJSONString(new JsonResult(code, msg, new HashMap<>(0)));
    }
}
