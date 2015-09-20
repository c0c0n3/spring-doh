package app.config;

import java.nio.charset.StandardCharsets;
import java.util.List;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import app.web.HomeController;


/**
 * Additional Spring bean wiring and configuration for the web app.
 */
@Configuration
@ComponentScan(basePackageClasses={HomeController.class})
@EnableWebMvc
@Profile(Profiles.WebApp)
public class WebWiring extends WebMvcConfigurerAdapter {

    @Override 
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(new StringHttpMessageConverter(StandardCharsets.UTF_8));
    }
    /* NB this overrides the default string converter which uses ISO-8859-1 as
     * default character encoding. The default encoding is what this converter
     * uses if the response content type has no charset parameter.
     * So with this set up, we can produce response strings without having to
     * specify UTF-8 as charset every time.
     */
}
