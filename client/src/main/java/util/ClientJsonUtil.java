package util;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;

public class ClientJsonUtil {
    public static String toJson(Object obj) {
        return JSON.toJSONString(obj);
    }

    public static <T> T parseJson(String json, Class<T> clazz) {
        return JSON.parseObject(json, clazz);
    }

    public static JSONArray toArray(String jsonString){
        return JSONArray.parseArray(jsonString);
    }
}
