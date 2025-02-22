package io.github.kimmking.gateway;


import com.jianjoy.http.SimpleHttpServer;
import io.github.kimmking.gateway.inbound.HttpInboundServer;

import java.util.Arrays;

public class NettyServerApplication {

    public final static String GATEWAY_NAME = "NIOGateway";
    public final static String GATEWAY_VERSION = "3.0.0";

    public static void main(String[] args) throws Exception {
        //初始化后台服务
        SimpleHttpServer.main(args);

        String proxyPort = System.getProperty("proxyPort", "8888");

        // 这是之前的单个后端url的例子
//        String proxyServer = System.getProperty("proxyServer","http://localhost:8088");
//          //  http://localhost:8888/api/hello  ==> gateway API
//          //  http://localhost:8088/api/hello  ==> backend service
        // java -Xmx512m gateway-server-0.0.1-SNAPSHOT.jar  #作为后端服务


        // 这是多个后端url走随机路由的例子
        String proxyServers = System.getProperty("proxyServers", "http://localhost:8801,http://localhost:8802");
        int port = Integer.parseInt(proxyPort);
        System.out.println(GATEWAY_NAME + " " + GATEWAY_VERSION + " starting...");
        HttpInboundServer server = new HttpInboundServer(port, Arrays.asList(proxyServers.split(",")));
        System.out.println(GATEWAY_NAME + " " + GATEWAY_VERSION + " started at http://localhost:" + port + " for server:" + server.toString());

        System.out.println("请访问接口地址：http://localhost:"+port+"/h1");
        try {
            server.run();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
