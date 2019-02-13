package com.tqy.cams.utils;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.util.JSONUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Title: JsonUtils.java
 * @Package com.njyy.system.util
 * @Description:基于高性能的jackjson的json解析工具类
 */
public class JsonUtils {

    private static Logger logger = LoggerFactory.getLogger(JsonUtils.class);

    public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    private static final ObjectMapper defaultObjectMapper = new ObjectMapper();

    private static final ObjectMapper customObjectMapper = new ObjectMapper();

    static {
        defaultObjectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        defaultObjectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        defaultObjectMapper.setDateFormat(new SimpleDateFormat(DEFAULT_DATE_FORMAT));

        customObjectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        customObjectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        customObjectMapper.setDateFormat(new SimpleDateFormat(DEFAULT_DATE_FORMAT));
        //  customObjectMapper.setSerializerFactory(CustomBeanSerializerFactory.instance);

//        String[] dateFormats = new String[] {DEFAULT_DATE_FORMAT};
//        JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher(dateFormats));
        JSONUtils.getMorpherRegistry().registerMorpher(new TimestampToDateMorpher());
    }

    /**
     * 将Json格式的字符串转换成指定对象组成的List返回
     *
     * @param jsonString Json格式的字符串
     * @param pojoClass  转换后的List中对象类型
     * @return 转换后的List对象
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public static List jsonToList(String jsonString, Class pojoClass) {
        JSONArray jsonArray = JSONArray.fromObject(jsonString);
        JSONObject jsonObject;
        Object pojoValue;
        List list = new ArrayList();
        for (int i = 0; i < jsonArray.size(); i++) {
            jsonObject = jsonArray.getJSONObject(i);
            pojoValue = JSONObject.toBean(jsonObject, pojoClass);
            list.add(pojoValue);
        }
        return list;
    }


    public static <T> List jsonToList(String jsonString, Class pojoClass, Map<String, Class> classMap) {
        List<T> list = new ArrayList<>();
        JSONArray jsonarr = JSONArray.fromObject(jsonString);
        if (jsonarr.size() > 0) {
            for (int i = 0; i < jsonarr.size(); i++) {
                JSONObject job = jsonarr.getJSONObject(i);  // 遍历 jsonarray 数组，把每一个对象转成 json 对象
                T ai = (T) JSONObject.toBean(job, pojoClass, classMap);
                list.add(ai);
            }
        }
        return list;
    }

    /**
     * 普通javabean转json字符串
     *
     * @param <T>
     * @param object
     * @return
     * @throws Exception
     */
    public static <T> String objectToJson(T object){
        try {
            return defaultObjectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            logger.error(String.format("obj=[%s]", object.toString()), e);
        }
        return null;
    }

    /**
     * 普通javabean转json字符串
     *
     * @param <T>
     * @param object
     * @param nullStringToEmptyString - javabean属性中的String类型属性字段为null时将转换成空串""
     * @return
     * @throws Exception
     */
    public static <T> String objectToJson(T object, boolean nullStringToEmptyString) throws Exception {
        if (nullStringToEmptyString) {
            return customObjectMapper.writeValueAsString(object);
        }
        return defaultObjectMapper.writeValueAsString(object);
    }

    /**
     * 字符串转普通javabean
     * 注:仅供非集合类的普通javabean使用,且支持javabean属性中存在集合(List)嵌套集合(List)这样的情况
     *
     * @param <T>
     * @param json
     * @param clazz
     * @return
     * @throws Exception
     */
    public static <T> T jsonToObject(String json, Class<T> clazz){
        try {
            return defaultObjectMapper.readValue(json, clazz);
        } catch (IOException e) {
            logger.error(String.format("json=[%s]", json), e);
        }
        return null;
    }

    /**
     * 字符串转集合类
     * 注:仅供集合类使用,解决集合泛型化及多重集合嵌套(如List<User<List<Account>>)等
     * 示例： List<User> userList = jsonToObject("["username":"jack","accounts":["accountId":"","amount":1200.00]]", new TypeReference<List<User>>(){});
     *
     * @param <T>
     * @param json
     * @param typeReference
     * @return
     * @throws Exception
     */
    public static <T> T jsonToObject(String json, TypeReference<T> typeReference) throws Exception {
        return defaultObjectMapper.readValue(json, typeReference);
    }

    public static ObjectMapper getDefaultObjectMapper() {
        return defaultObjectMapper;
    }

    public static ObjectMapper getCustomObjectMapper() {
        return customObjectMapper;
    }
}