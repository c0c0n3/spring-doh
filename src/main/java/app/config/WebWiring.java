package app.config;

import static util.Arrayz.array;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import app.web.TripstersController;


/**
 * Spring bean wiring configuration.
 */
@Configuration
@ComponentScan(basePackageClasses={TripstersController.class})
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
