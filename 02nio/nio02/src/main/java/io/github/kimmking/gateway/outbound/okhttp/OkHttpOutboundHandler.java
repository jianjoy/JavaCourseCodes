package io.github.kimmking.gateway.outbound.okhttp;

import com.jianjoy.http.HttpUtil;
import io.github.kimmking.gateway.filter.MyHttpResponseFilter;
import io.github.kimmking.gateway.router.MyEndpointRouter;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static io.netty.handler.codec.http.HttpHeaderNames.CONNECTION;
import static io.netty.handler.codec.http.HttpHeaderValues.KEEP_ALIVE;

public class OkHttpOutboundHandler {
    private static Logger logger = LoggerFactory.getLogger(OkHttpOutboundHandler.class);
    private List<String> endpoints;

    public OkHttpOutboundHandler(List<String> servers) {
        this.endpoints = servers;
    }

    private MyEndpointRouter myEndpointRouter = new MyEndpointRouter();
    private MyHttpResponseFilter responseFilter = new MyHttpResponseFilter();

    public void handle(FullHttpRequest fullRequest, ChannelHandlerContext ctx) {
        String serverUrl = myEndpointRouter.route(endpoints);
        System.out.println("random server url:" + serverUrl);
        FullHttpResponse response = null;
        try {
            String htmlContent = HttpUtil.sendGet(serverUrl);
            response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK
                    , Unpooled.wrappedBuffer(htmlContent.getBytes("UTF-8")));
            responseFilter.filter(response);
            response.headers().set("Content-Type", "application/json");
            response.headers().setInt("Content-Length", response.content().readableBytes());
            System.out.println("response header:"+response.headers());
        } catch (Exception e) {
            logger.error("调用后台接口出错", e);
            response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.NO_CONTENT);
        } finally {
            if (fullRequest != null) {
                if (!io.netty.handler.codec.http.HttpUtil.isKeepAlive(fullRequest)) {
                    ctx.write(response).addListener(ChannelFutureListener.CLOSE);
                } else {
                    response.headers().set(CONNECTION, KEEP_ALIVE);
                    ctx.write(response);
                }
            }
        }

    }
}
