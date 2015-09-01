package app.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import app.beans.StdoutVisualizerBean;
import app.core.cyclic.CycleVisualizer;
import app.core.trips.Tripster;
import app.core.trips.TripsterGroup;

/**
 * Spring bean wiring configuration.
 */
@Configuration
@ComponentScan(basePackageClasses={Wiring.class, StdoutVisualizerBean.class})
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
