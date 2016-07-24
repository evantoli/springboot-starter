# Springboot Starter

This is a standalone SpringBoot application that I use as a minimal starting 
 point for lot's of experimentation.

I have configured it to be ready to use.

# Main application entry point

The Java entry point for the application is the `static void main` method of 
 the `Application` class.

~~~Java
@Configuration
@EnableAutoConfiguration
@ComponentScan
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
~~~

The annotations on this class do the following:

* `@Configuration` – Identifies the class as one that contains Java based Spring configuration.
* `@EnableAutoConfiguration` – Instructs SpringBoot to look for and configure anything on
 your classpath that you may have missed. I prefer to not use this and be in control but for
 quick throw-away proofs-of-concept it can be handy to leave this on.
* `@ComponentScan` – Instructs SpringBoot to automatically scan for Spring components.

# Server configuration

SpringBoot provides auto-configuration of MVC and HATEOAS HAL Hypermedia. 
 If you do need to tweak or adapt the auto-configuration then you extend Spring's
 `WebMvcConfigurerAdapter` class, overriding the methods as you need.

In this starter application I don't provide a root web-page as I wish
 it to be a RESTful API server. So I redirect all root web requests
 to the Swagger RESTful API documentation page.

~~~Java
@Configuration
public class WebMvcConfiguration extends WebMvcConfigurerAdapter {
     
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {

        // This application does not have a root web-page. So I want to redirect
        // root requests to the Swagger UI RESTful API documentation page.
        registry.addViewController("/").setViewName("redirect:/swagger/index.html");
    }
}
~~~

If you do need to take full control of Spring MVC or HATEOAS HAL Hypermedia configuration
 then can add the following two annotations to your configuration class:

* <a href="http://docs.spring.io/spring-boot/docs/current/reference/html/boot-features-developing-web-applications.html#boot-features-spring-mvc-auto-configuration" target="_blank">`@EnableWebMvc`</a> – 
 Let's us take full control of Spring MVC configuration. 
* <a href="http://docs.spring.io/spring-boot/docs/current/reference/html/boot-features-developing-web-applications.html#boot-features-spring-hateoas" target="_blank">`@EnableHypermediaSupport`</a> – 
 Let's us take full control of configuration for HATEOAS
 to develop RESTful APIs that makes use of hypermedia.

Keep in mind that if you use these annotations then you may need to do more work 
 to get to a point where you mimic all the SpringBoot auto-configuration initial state.

## Serving static content

Springboot will automatically serve static content from the following four 
classpath locations:

* `/META-INF/resources/`
* `/resources/`
* `/static/`
* `/public/`

If your application doesn't serve static content from these locations
 then it is probably because you 
 have added the `@EnableWebMvc` annotation to a configuration class and so 
 SpringBoot is no longer auto-configured for these locations. You will need
 to configure where you wish to serve static content from.

## HATEOAS REST services

SpringBoot applications are auto-configured. To take advantage of HATEOAS
you will need to create or extend `Resource` classes and provide resource
assemblers that extend `ResourceAssembler`.


# Swagger REST API documentation

This application has been built with Swagger to expose the REST API documentation. 
The URLs to browser to are:

* <a href="http://localhost:8080/swagger" target="_blank">/swagger</a>
* <a href="http://localhost:8080/swagger-resources" target="_blank">/swagger-resources</a>
* <a href="http://localhost:8080/v2/api-docs" target="_blank">/v2/api-docs</a>

The Swagger-UI has been built into the application by cloning the 
<a href="https://github.com/swagger-api/swagger-ui" target="_blank">swagger-ui</a>
GitHub project and then taking the `dist` directory and placing it—renamed as `swagger`—into 
this application's `src/main/resources/static` directory.
