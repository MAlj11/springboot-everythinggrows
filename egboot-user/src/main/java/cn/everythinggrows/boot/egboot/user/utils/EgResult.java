package cn.everythinggrows.boot.egboot.user.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class EgResult {
    // 定义jackson对象
    private static final ObjectMapper MAPPER = new ObjectMapper();

    // 响应业务状态
    private Integer status;

    // 响应消息
    private String msg;

    // 响应中的数据
    private Object data;

    public static EgResult build(Integer status, String msg, Object data) {
        return new EgResult(status, msg, data);
    }

    public static EgResult ok(Object data) {
        return new EgResult(data);
    }

    public static EgResult ok() {
        return new EgResult(null);
    }

    public EgResult() {

    }

    public static EgResult build(Integer status, String msg) {
        return new EgResult(status, msg, null);
    }

    public static EgResult error(Integer status, String msg){
        return new EgResult(status,msg,null);
    }

    public static EgResult systemError(){
        return EgResult.error(100000,"system error");
    }

    public EgResult(Integer status, String msg, Object data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    public EgResult(Object data) {
        this.status = 200;
        this.msg = "OK";
        this.data = data;
    }

//    public Boolean isOK() {
//        return this.status == 200;
//    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    /**
     * 将json结果集转化为EgResult对象
     *
     * @param jsonData json数据
     * @param clazz    EgResult中的object类型
     * @return
     */
    public static EgResult formatToPojo(String jsonData, Class<?> clazz) {
        try {
            if (clazz == null) {
                return MAPPER.readValue(jsonData, EgResult.class);
            }
            JsonNode jsonNode = MAPPER.readTree(jsonData);
            JsonNode data = jsonNode.get("data");
            Object obj = null;
            if (clazz != null) {
                if (data.isObject()) {
                    obj = MAPPER.readValue(data.traverse(), clazz);
                } else if (data.isTextual()) {
                    obj = MAPPER.readValue(data.asText(), clazz);
                }
            }
            return build(jsonNode.get("status").intValue(), jsonNode.get("msg").asText(), obj);
        } catch (Exception e) {
            return null;
        }
    }
}
