package com.jianjoy.http;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * Author: zhoujian
 * Description:http工具
 * Date: 2021/5/16 22:34
 */
public final class HttpUtil {


    private static final OkHttpClient CLIENT = new OkHttpClient();

    private HttpUtil() {

    }

    static {
        CLIENT.setConnectTimeout(30, TimeUnit.SECONDS);
        CLIENT.setReadTimeout(30, TimeUnit.SECONDS);
    }


    /**
     * 发送get请求获取返回内容
     *
     * @param url
     * @return
     * @throws IOException
     */
    public static String sendGet(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();
        Response response = CLIENT.newCall(request).execute();
        return response.body().string();
    }

    public static void main(String[] args) throws Exception {
        SimpleHttpServer.main(args);
        System.out.println(sendGet("http://localhost:8801"));
    }


}
