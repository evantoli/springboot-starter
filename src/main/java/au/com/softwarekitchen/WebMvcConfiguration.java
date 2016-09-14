package au.com.softwarekitchen;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * This is main configuration class for configuring the Spring MVC.
 *
 * There is no need to add the <tt>@EnableWebMvc</tt> or <tt>@EnableHypermediaSupport</tt>
 * if you're happy with SpringBoot auto-configuration of MVC and HATEOAS HAL Hypermedia.
 */
@Configuration
public class WebMvcConfiguration extends WebMvcConfigurerAdapter {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {

        // This application does not have a root web-page. So I want to redirect
        // root requests to the Swagger UI RESTful API documentation page.
        registry.addViewController("/").setViewName("redirect:/swagger-ui.html");
    }
}
