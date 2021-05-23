package io.github.kimmking.gateway.filter;

import io.netty.handler.codec.http.FullHttpResponse;

import java.util.UUID;

/**
 * Author: zhoujian
 * Description:
 * Date: 2021/5/23 23:00
 */
public class MyHttpResponseFilter implements HttpResponseFilter {


    @Override
    public void filter(FullHttpResponse response) {
        response.headers().set("x-server-time", System.currentTimeMillis());
        response.headers().set("x-id", UUID.randomUUID().toString());
    }
}