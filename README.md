# Prêt-à-Springboot

This is a standalone SpringBoot application that I use as a minimal starting point for lot's of experimentation.

I have configured it to be ready to use.

# Server configuration

You can enable Spring Web MVC by adding the `@EnableWebMvs` annotation to one of your configuration classes.

This example application has a configuration class that makes 
this magic happen is called `ServerConfiguration`:

~~~Java
@Configuration
@EnableWebMvc
@EnableHypermediaSupport(type= {EnableHypermediaSupport.HypermediaType.HAL})
public class ServerConfiguration extends WebMvcAutoConfiguration {
     
     // ...
}
~~~

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
