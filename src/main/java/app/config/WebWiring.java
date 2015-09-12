package app.config;

import static util.Arrayz.array;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

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
    
}
/* NOTE. To go beyond minimal configuration, we'd need getServletConfigClasses
 * to also return a class implementing WebMvcConfigurer or out of convenience, 
 * extending WebMvcConfigurerAdapter. 
 */
