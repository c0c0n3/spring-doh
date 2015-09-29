package app.config;

import static util.Streams.asList;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.FilterType;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import util.config.ConfigProvider;
import app.aspects.ArrayCycleFix;
import app.beans.StdoutVisualizerBean;
import app.core.cyclic.CycleVisualizer;
import app.core.trips.Tripster;
import app.core.trips.TripsterGroup;
import app.core.trips.TripsterSpotter;

/**
 * Spring bean wiring configuration.
 */
@Configuration
@ComponentScan(
    basePackageClasses = {
        Wiring.class, StdoutVisualizerBean.class, ArrayCycleFix.class },
    excludeFilters = {
        @Filter(type = FilterType.ANNOTATION, value = EnableWebMvc.class) }
)
@EnableAspectJAutoProxy
public class Wiring {
    
    @Autowired
    private CycleVisualizer<String> sharedVisualizer;
    
    @Autowired
    private ConfigProvider<TripsterConfig> tripsties;
    
    
    @Bean
    public TripsterSpotter<String> tripsterSpotter() {
        List<Tripster<String>> happyBunch = asList(
                TripsterConfigMapper.newWithStringArray()
                                    .defaultFromConfig(tripsties));
        
        return new TripsterSpotter<>(new TripsterGroup<>(happyBunch),
                                     sharedVisualizer);
    }
    
}
/* NOTE. Quite a silly way of wiring beans but gives something to play with in
 * the tests. For starters, we make TripsterSpotter a bean but it is a generic
 * class and implements no interfaces so we use the "concrete" type. On the
 * other hand we instantiate the StdoutVisualizerBean using the CycleVisualizer
 * interface; this bean is a prototype but is linked to the singleton tripster
 * spotter and so it will be shared even if it was declared as prototype.  
 */