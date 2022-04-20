package com.example.mmykgateway;

import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import reactor.core.publisher.Mono;

@Configuration
public class MyBaby {


    @Primary
    @Bean(value = "hostKeyResolver")
    public KeyResolver hostKeyResolver() {
        return exchange -> {
            String hostAddress = exchange.getRequest().getRemoteAddress().getAddress().getHostAddress();
            System.out.println("::" + hostAddress);
            return Mono.just(hostAddress);
        };
    }

    @Bean(name="apiKeyResolver")
    public KeyResolver apiKeyResolver() {
        return exchange -> {
            String path = exchange.getRequest().getPath().value();
            System.out.println("::" + path);
            return Mono.just(path);
        };
    }

    @Bean("userKeyResolver")
    KeyResolver userKeyResolver() {
        return exchange -> {
            String userId = exchange.getRequest().getQueryParams().getFirst("userId");
            System.out.println("::" + userId);
            return Mono.just(userId);
        };
    }




    /**
     * 按照Path限流
     *
     * @return key
     */
//    @Bean
//    KeyResolver pathKeyResolver() {
//        return exchange -> Mono.just(exchange.getRequest().getPath().toString());
//    }


//    @Bean(value = "ipKeyResolver")
//    KeyResolver ipKeyResolver() {
//        return exchange -> {
//            String ip = exchange.getRequest().getRemoteAddress().getAddress().getHostAddress();
//            return Mono.just(ip);
//        };
//    }

//    @Bean
//    KeyResolver userKeyResolver() {
//        return exchange -> Mono.just(exchange.getRequest().getQueryParams().getFirst("userId"));
//    }
//
//    @Bean
//    KeyResolver ipKeyResolver() {
//        return exchange -> Mono.just(exchange.getRequest().getHeaders().getFirst("X-Forwarded-For"));
//    }
//
//    @Bean
//    KeyResolver apiKeyResolver() {
//        return exchange -> Mono.just(exchange.getRequest().getPath().value());
//    }
}
