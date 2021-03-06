package com.nit.hjh.gateway.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.function.Consumer;

/**
 * GatewayFilter 需要配置
 * GlobalFilter  全局过滤器
 */
@Component
public class TokenFilter extends AbstractGatewayFilterFactory implements GatewayFilter , Ordered {

    @Override
    public GatewayFilter apply(Object config) {
        return (exchange, chain) -> {
            return filter(exchange,chain);
        };
    }

    Logger logger= LoggerFactory.getLogger( TokenFilter.class );
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String token = exchange.getRequest().getQueryParams().getFirst("token");
//        if (token == null || token.isEmpty()) {
//            logger.info( "token is empty..." );
//            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
//            return exchange.getResponse().setComplete();
//        }
        return chain.filter(exchange);
    }
    //加载顺序，值越小越优先
    @Override
    public int getOrder() {
        return 5;
    }
}
