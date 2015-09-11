package app.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Profile;

import app.aspects.ArrayCycleFix;
import app.beans.StdoutVisualizerBean;
import app.core.cyclic.CycleVisualizer;
import app.core.trips.Tripster;
import app.core.trips.TripsterGroup;

/**
 * Spring bean wiring configuration.
 */
@Configuration
@ComponentScan(basePackageClasses={
        Wiring.class, StdoutVisualizerBean.class, ArrayCycleFix.class})
@EnableAspectJAutoProxy
@Profile(Profiles.NotWebApp)
public class Wiring {
    
    @Autowired
    private CycleVisualizer<String> sharedVisualizer;
    
    @Autowired
    private ConfigProvider<List<TripsterConfig>> tripsties;
    
    
    @Bean
    public TripsterGroup<String> tripsterGroup() {
        List<Tripster<String>> happyBunch = 
                TripsterConfigMapper.fromConfig(tripsties, sharedVisualizer);

        return new TripsterGroup<>(happyBunch);
    }
    
}
/* NOTE. Quite a silly way of wiring beans but gives something to play with in
 * the tests. For starters, we make TripsterGroup a bean but it is a generic
 * class and implements no interfaces so we use the "concrete" type. On the
 * other hand we instantiate the StdoutVisualizerBean using the CycleVisualizer
 * interface; this bean is a prototype but is linked to the singleton tripster
 * group through Tripster's (none of which is a bean) and so it will be shared
 * by all of them even if it was declared as prototype.  
 */