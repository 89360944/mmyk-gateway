package com.example.mmykgateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class MmykGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(MmykGatewayApplication.class, args);
    }

//    @Bean
//    public OpenAPI defineOpenApi() {
//        return new OpenAPI()..addServersItem(new Server().url("worini").description("nmb"));
//    }
//    @Autowired
//    RouteDefinitionLocator locator;
//
//    @Bean
//    public List<GroupedOpenApi> apis() {
//        List<GroupedOpenApi> groups = new ArrayList<>();
//        List<RouteDefinition> definitions = locator.getRouteDefinitions().collectList().block();
//        assert definitions != null;
//        definitions.stream().filter(routeDefinition -> routeDefinition.getId().matches(".*-service")).forEach(routeDefinition -> {
//            String name = routeDefinition.getId().replaceAll("-service", "");
//            System.out.println(":::::::::::"+name);
//            //groups.add(GroupedOpenApi.builder().pathsToMatch("/" + name + "/**").group("zzzz").build());
//            groups.add(GroupedOpenApi.builder().pathsToMatch("/" + name + "/**").group("sssssssssssss").addOpenApiCustomiser(new OpenApiCustomiser() {
//                @Override
//                public void customise(OpenAPI openApi) {
//                    openApi.addServersItem(new Server().url("worini").description("nmb"));
//                }
//            }).build());
//        });
//        return groups;
//    }

//    @Bean
//    public OpenAPI customOpenAPI(@Value("${springdoc.version}") String appVersion) {
//        return new OpenAPI()
//                .components(new Components().addSecuritySchemes("basicScheme", new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("basic"))
//                        .addParameters("myHeader1", new Parameter().in("header").schema(new StringSchema()).name("myHeader1")).addHeaders("myHeader2", new Header().description("myHeader2 header").schema(new StringSchema())))
//                .info(new Info()
//                        .title("Petstore API")
//                        .version(appVersion)
//                        .description("This is a sample server Petstore server. You can find out more about Swagger at [http://swagger.io](http://swagger.io) or on [irc.freenode.net, #swagger](http://swagger.io/irc/). For this sample, you can use the api key `special-key` to test the authorization filters.")
//                        .termsOfService("http://swagger.io/terms/")
//                        .license(new License().name("Apache 2.0").url("http://springdoc.org")));
//    }

}
