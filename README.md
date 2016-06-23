# Prêt-à-Springboot

This is a standalone SpringBoot application that I use as a minimal starting point for lot's of experimentation.

I have configured it to be ready to use.

# Main application entry point

The Java entry point for the application is the `static void main` method of the `Application` class.

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

You can enable Spring Web MVC by adding the `@EnableWebMvs` annotation to one of your configuration classes.

I have added this annotation to a class called `ServerConfiguration`. 
This class will become a handy place to then configure other server related 
features and functionality like enabling HATEOAS support for RESTful services.

~~~Java
@Configuration
@EnableWebMvc
@EnableHypermediaSupport(type= {EnableHypermediaSupport.HypermediaType.HAL})
public class ServerConfiguration extends WebMvcAutoConfiguration {
     
     // ...
}
~~~

The annotations on this class do the following:

* `@EnableWebMvc` – Let's us take full control of Spring MVC configuration.
* `@EnableHypermediaSupport` – Let's us take full control of configuration for HATEOAS
 to develop RESTful APIs that makes use of hypermedia.

Removing both of these annotations will result in SpringBoot providing auto-configuration for
Spring MVC and HATEOAS support that may be adequate for many applications.

## Serving static content

So long as you have a configuration class that extends `WebMvcAutoConfiguration` 
Springboot will automatically serve static content from the following four 
classpath locations:

* `/META-INF/resources/`
* `/resources/`
* `/static/`
* `/public/`

## HATEOAS REST services

You can enable HATEOAS with the `@EnableHypermediaSupport` to one of your configuration classes.


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
