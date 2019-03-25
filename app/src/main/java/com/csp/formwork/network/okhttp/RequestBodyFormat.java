package com.csp.formwork.network.okhttp;

import com.google.gson.Gson;
import com.zqyl.utillib.LogCat;

import java.lang.reflect.Field;

import okhttp3.FormBody;
import okhttp3.RequestBody;


/**
 * 请求参数格式化
 * Created by csp on 2019/3/13.
 * Modified by csp on 2019/3/25.
 *
 * @version 1.0.1
 */
@SuppressWarnings("unused")
public class RequestBodyFormat {

    /**
     * Gson 单例
     */
    private static class Instance {
        static Gson mGson = new Gson();
    }

    /**
     * @param request 请求参数对象
     * @return 表单格式 FormBody
     */
    public static RequestBody getJsonBody(Object request) {
        try {
            String json = Instance.mGson.toJson(request);
            return RequestBody.create(BaseRetrofit.MEDIA_TYPE_JSON, json);
        } catch (Throwable t) {
            LogCat.printStackTrace(t);
            return null;
        }
    }

    /**
     * @param request 请求参数对象
     * @return 表单格式 FormBody
     */
    public static FormBody getFormBody(Object request) {
        FormBody.Builder builder = new FormBody.Builder();
        try {
            Field[] fields = request.getClass().getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                String key = field.getName();
                Object value = field.get(request);
                builder.add(key, value == null ? "" : value.toString());
            }
        } catch (IllegalAccessException e) {
            LogCat.printStackTrace(e);
            return null;
        }
        return builder.build();
    }
}
