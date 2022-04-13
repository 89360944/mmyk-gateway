package com.example.mmykgateway;



import com.fasterxml.jackson.core.JsonProcessingException;
import org.springdoc.core.*;
import org.springdoc.webflux.api.MultipleOpenApiWebFluxResource;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionLocator;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Configuration
public class SwaggerConfig {
    @Autowired
    RouteDefinitionLocator locator;
    @Autowired
    DiscoveryClient discoveryClient;
    @Autowired
    ApplicationContext applicationContext;

    @Bean
    RestTemplate restTemplate() {
        return new RestTemplate();
    }
    // 手动根据路由定义的服务，创建分组API文档
    @Bean
    public List<GroupedOpenApi> apis() {
        List<GroupedOpenApi> groups = new ArrayList<>();
        List<RouteDefinition> definitions = locator.getRouteDefinitions().collectList().block();
        definitions.stream().forEach(routeDefinition -> {
            String name = routeDefinition.getUri().getHost();
            GroupedOpenApi groupedOpenApi =
                    GroupedOpenApi.builder()
                            .group(name)
                            .pathsToMatch(
                                    routeDefinition.getPredicates().get(0).getArgs().get("pattern")
                            ).build();
            groups.add(groupedOpenApi);
        });
        return groups;
    }
    // 重载默认的多重API定义接口，如果请求的分组为注册的服务ID，则返回对应服务的API定义
    @Bean
    MultipleOpenApiWebFluxResource multipleOpenApiWebFluxResource(List<GroupedOpenApi> groupedOpenApis, ObjectFactory<OpenAPIService> defaultOpenAPIBuilder, AbstractRequestService requestBuilder, GenericResponseService responseBuilder, OperationService operationParser, SpringDocConfigProperties springDocConfigProperties, SpringDocProviders springDocProviders) {

        return new MultipleOpenApiWebFluxResource(groupedOpenApis, defaultOpenAPIBuilder, requestBuilder, responseBuilder, operationParser, springDocConfigProperties, springDocProviders) {
            @Override
            public Mono<String> openapiJson(ServerHttpRequest serverHttpRequest, String apiDocsUrl, String group, Locale locale) throws JsonProcessingException {
                List<ServiceInstance> serviceInstances = discoveryClient.getInstances(group);
                if (!CollectionUtils.isEmpty(serviceInstances)) {
                    String gatewayUri = serverHttpRequest.getURI().getScheme() + "://" + serverHttpRequest.getURI().getHost();
                    if (serverHttpRequest.getURI().getPort() != -1) {
                        gatewayUri += ":" + serverHttpRequest.getURI().getPort();
                    }
                    gatewayUri += "/" + group;
                    String serviceUri = serviceInstances.get(0).getUri().toString();
                    String url = serviceUri + "/v3/api-docs";
                    String openapiJson = restTemplate().getForObject(url, String.class);
                    openapiJson = openapiJson.replace(serviceUri, gatewayUri);
                    return Mono.just(openapiJson);
                }
                return super.openapiJson(serverHttpRequest, apiDocsUrl, group, locale);
            }
        };
    }
}

