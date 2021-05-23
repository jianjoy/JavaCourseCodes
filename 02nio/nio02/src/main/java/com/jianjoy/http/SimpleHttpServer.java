package com.jianjoy.http;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Date;

/**
 * Author: zhoujian
 * Description:简单http server
 * Date: 2021/5/16 22:48
 */
public class SimpleHttpServer {


    public static void main(String[] args) throws IOException {
        createHttpServer(8801).start();
        createHttpServer(8802).start();
        System.out.println("SimpleHttpServer started.");
    }

    private static HttpServer createHttpServer(int port) throws IOException {
        HttpServer httpServer = HttpServer.create(new InetSocketAddress(port), 0);
        httpServer.createContext("/", new HttpHandler() {
            @Override
            public void handle(HttpExchange httpExchange) throws IOException {
                byte[] content = ("Hello").getBytes("utf-8");
                System.out.println("server port:"+port+",handle request."+new Date());
                httpExchange.getResponseHeaders().add("Content-Type", "application/json; charset=UTF-8");
                httpExchange.sendResponseHeaders(200, content.length);
                httpExchange.getResponseBody().write(content);
                httpExchange.close();
            }
        });
        return httpServer;
    }

}
