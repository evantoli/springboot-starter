package au.com.softwarekitchen;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static springfox.documentation.builders.PathSelectors.regex;

@Configuration
@EnableSwagger2
public class SwaggerConfiguration {

    @Bean
    public Docket newsApi() {
        return new Docket(DocumentationType.SWAGGER_2)
//                .groupName("API")
                .apiInfo(apiInfo())
                .select()
                .paths(regex("/customers.*"))
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Springboot example application")
                .description("The example application's REST API documented here with Swagger")
                .termsOfServiceUrl("http://some.url")
                .contact("Evan Toli")
                .license("Apache License Version 2.0")
                .licenseUrl("http://some.url")
                .version("2.0")
                .build();
    }
}
