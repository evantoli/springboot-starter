package au.com.softwarekitchen;

import com.google.common.base.Predicate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.web.PagedResourcesAssembler;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static com.google.common.base.Predicates.not;
import static com.google.common.base.Predicates.or;

@Configuration
@EnableSwagger2
public class SwaggerConfiguration {

    @Bean
    public Docket restApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("Default API")
                .ignoredParameterTypes(PagedResourcesAssembler.class)
                .apiInfo(apiInfo())
                .select()
                .paths(restApiPaths())
                .build();
    }

    @Bean
    public Docket monitoringApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("Monitoring API")
                .ignoredParameterTypes(PagedResourcesAssembler.class)
                .apiInfo(apiInfo())
                .select()
                .paths(monitoringApiPaths())
                .build();
    }

    private Predicate<String> restApiPaths() {
        return or(
                PathSelectors.regex("/actor.*"),
                PathSelectors.regex("/movies.*"));
    }

    private Predicate<String> monitoringApiPaths() {
        return not(restApiPaths());
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Springboot example application")
                .description("The example application's REST API documented here with Swagger")
                .termsOfServiceUrl("http://some.url")
                .contact(new Contact("Evan Toli", "", ""))
                .license("Apache License Version 2.0")
                .licenseUrl("http://some.url")
                .version("2.0")
                .build();
    }
}
