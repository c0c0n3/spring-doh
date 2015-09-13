package integration.spring.lifecycle;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import app.config.Profiles;
import app.config.Wiring;
import app.core.trips.TripsterSpotter;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=TripsterSpotterSingletonTest.class)
@ComponentScan(basePackageClasses={Wiring.class})
@ActiveProfiles({Profiles.HardCodedConfig})
public class TripsterSpotterSingletonTest {

    @Autowired
    private ApplicationContext context;
    
    @Autowired
    private TripsterSpotter<String> instance1;
    
    @Autowired
    private TripsterSpotter<String> instance2;
    
    private void assertSameInstances(Object fst, Object snd) {
        assertNotNull(fst);
        assertNotNull(snd);
        assertTrue(fst == snd);
    }
    
    @Test
    public void sameObjectForEachAutowiredField() {
        assertSameInstances(instance1, instance2);
    }
    
    @Test
    @SuppressWarnings("unchecked")
    public void sameObjectReturnedByEachGetBeanCall() {
        TripsterSpotter<String> fst = context.getBean(TripsterSpotter.class);
        TripsterSpotter<String> snd = context.getBean(TripsterSpotter.class);
        
        assertSameInstances(fst, snd);
    }
    
    @Test
    public void visualizerShared() {
        assertSameInstances(instance1.getVisualizer(), instance2.getVisualizer());        
    }
    
}
