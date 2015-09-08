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
import app.core.cyclic.CycleVisualizer;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=CycleVisualizerPrototypeTest.class)
@ComponentScan(basePackageClasses={Wiring.class})
@ActiveProfiles(Profiles.HardCodedConfig)
public class CycleVisualizerPrototypeTest {

    @Autowired
    private ApplicationContext context;
    
    @Autowired
    private CycleVisualizer<String> instance1;
    
    @Autowired
    private CycleVisualizer<String> instance2;
    
    private void assertDifferentInstances(Object fst, Object snd) {
        assertNotNull(fst);
        assertNotNull(snd);
        assertTrue(fst != snd);
    }
    
    @Test
    public void newObjectForEachAutowiredField() {
        assertDifferentInstances(instance1, instance2);
    }
    
    @Test
    @SuppressWarnings("unchecked")
    public void newObjectReturnedByEachGetBeanCall() {
        CycleVisualizer<String> fst = context.getBean(CycleVisualizer.class);
        CycleVisualizer<String> snd = context.getBean(CycleVisualizer.class);
        
        assertDifferentInstances(fst, snd);
    }
    
}
