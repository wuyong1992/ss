package com.zzc.ss.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * @author JianGuo
 * on 2018/8/10
 * description:
 * Gson的工具类
 */
public class GsonUtil {


    /**
     * 简单的转换，格式化输出，用于DEBUG
     * @param o
     * @return
     */
    public static String objectToPrettyJson(Object o) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setPrettyPrinting();
        Gson gson = gsonBuilder.create();
        return gson.toJson(o);
    }

    /**
     * 对象转json
     * @param o
     * @return
     */
    public static String objectToJson(Object o){
        return new Gson().toJson(o);
    }

    /**
     * json转对象
     * @param json
     * @param clazz 要转成的对象类型
     * @param <T>
     * @return
     */
    public static <T> T jsonToObject(String json, Class<T> clazz){
        return new Gson().fromJson(json, clazz);
    }





}
