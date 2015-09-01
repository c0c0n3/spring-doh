package app.config;

import static java.util.stream.Collectors.toList;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import app.beans.StdoutVisualizerBean;
import app.core.cyclic.ArrayCycle;
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
    private CycleVisualizer<String> visualizer;
    
    @Autowired
    private ConfigProvider<List<TripsterConfig>> tripsties;
    
    private Tripster<String> fromConfig(TripsterConfig entry) {
        return new Tripster<>(
                entry.getName(), 
                new ArrayCycle<>(entry.getCycle()),
                visualizer); 
    }

    private List<Tripster<String>> tripsters() {
        return tripsties
              .defaultReadConfig()
              .stream()
              .map(this::fromConfig)
              .collect(toList());
    }
    
    @Bean
    public TripsterGroup<String> tripsterGroup() {
        return new TripsterGroup<>(tripsters());
    }
    
}
