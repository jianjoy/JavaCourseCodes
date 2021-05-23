package io.github.kimmking.gateway.router;

import java.security.SecureRandom;
import java.util.List;

/**
 * Author: zhoujian
 * Description: 随机路由
 * Date: 2021/5/23 23:03
 */
public class MyEndpointRouter implements HttpEndpointRouter {
    private static final SecureRandom RANDOM = new SecureRandom();

    @Override
    public String route(List<String> endpoints) {
        int randomIndex = RANDOM.nextInt(endpoints.size());
        return endpoints.get(randomIndex);
    }


}