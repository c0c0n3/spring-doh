package app.config;

import static util.Arrayz.array;

import java.nio.charset.StandardCharsets;

import javax.servlet.Filter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import util.servlet.http.CharEncodingFilter;
import app.web.HomeController;


/**
 * Additional Spring configuration for the web app.
 */
@Configuration
@ComponentScan(basePackageClasses={HomeController.class})
@EnableWebMvc
@Profile(Profiles.WebApp)
public class WebWiring 
    extends AbstractAnnotationConfigDispatcherServletInitializer {

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return array(Wiring.class);
    }
    // NB this would be a "no no oh d'oh" in a real webapp as the visualizer is shared...

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return array(WebWiring.class);
    }

    @Override
    protected String[] getServletMappings() {
        return array("/");
    }
    
    @Override
    protected Filter[] getServletFilters() {
        return array(CharEncodingFilter.Utf8Request(), 
                     CharEncodingFilter.Utf8Response());
    }
    
    @Bean
    public StringHttpMessageConverter stringHttpMessageConverter() {
        return new StringHttpMessageConverter(StandardCharsets.UTF_8);
    }
    
}
/* NOTE. To go beyond minimal configuration, we'd need getServletConfigClasses
 * to also return a class implementing WebMvcConfigurer or out of convenience, 
 * extending WebMvcConfigurerAdapter. 
 */
