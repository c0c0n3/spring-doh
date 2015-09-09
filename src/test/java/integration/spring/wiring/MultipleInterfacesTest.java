package integration.spring.wiring;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import app.beans.CycleTrackerBean;
import app.beans.TrackableCycleBean;
import app.config.Profiles;
import app.config.Wiring;
import app.core.cyclic.Cycle;
import app.core.cyclic.TrackableCycle;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=Wiring.class)
@ActiveProfiles(Profiles.HardCodedConfig)
public class MultipleInterfacesTest {

    @Autowired
    private Cycle<String> cycle;
    
    @Autowired
    private TrackableCycle<String> tracker;
    
    @Autowired
    private TrackableCycleBean<String> trackerBean;
    
    @Test
    public void cycleInstantiated() {
        assertNotNull(cycle);
    }
    
    @Test
    public void trackerInstantiated() {
        assertNotNull(tracker);
    }
    
    @Test
    public void trackerBeanInstantiated() {
        assertNotNull(trackerBean);
    }
    
    @Test
    public void autowiredDifferentObjectsAsBeanIsPrototype() {
        assertTrue(cycle != tracker);
        assertTrue(cycle != trackerBean);
        assertTrue(tracker != trackerBean);
    }
    
    @Test
    public void autowiredObjectInstancesOfSameImplementationClass() {
        assertTrue(cycle instanceof CycleTrackerBean<?>);
        assertTrue(tracker instanceof CycleTrackerBean<?>);
        assertTrue(trackerBean instanceof CycleTrackerBean<?>);
    }
    
}
