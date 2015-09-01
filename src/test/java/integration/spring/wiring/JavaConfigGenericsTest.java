package integration.spring.wiring;

import static util.Arrayz.array;
import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import app.config.Profiles;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=JavaConfigGenericsTest.class)
@ComponentScan(basePackageClasses={JavaConfigGenericsTest.class})
@ActiveProfiles(Profiles.HardCodedConfig)
public class JavaConfigGenericsTest {
    
    @Autowired
    @Qualifier("stringTracker")
    private TrackerBean<String> stringTracker;
    
    @Autowired
    @Qualifier("intTracker")
    private TrackerBean<Integer> intTracker;
    
    
    @Bean
    TrackerBean<String> stringTracker() {
        return new TrackerBean<>();
    }
    
    @Bean
    TrackerBean<Integer> intTracker() {
        return new TrackerBean<>();
    }
    
    @Test
    public void stringTrackerNotNull() {
        assertNotNull(stringTracker);
    }
    
    @Test
    public void intTrackerNotNull() {
        assertNotNull(intTracker);
    }
    
    @Test
    public void trackersAreNotSameInstanceWhenExplicitlyGivenDifferentIDs() {
        Object i = intTracker, s = stringTracker;
        assertTrue(i != s);
    }
    
    @Test
    public void trackersWorkWhenExplicitlyGivenDifferentIDs() {
        Integer[] intCycle = array(1, 2);
        String[] stringCycle = array("a", "b");
        
        // intTracker and stringTracker are *not* the same object, see test above...
        
        intTracker.track(intCycle);  
        stringTracker.track(stringCycle);
        
        intTracker.iterate(2);          
        stringTracker.iterate(2);
         
        Integer[] actualInts = intTracker.collectTracked(Integer[]::new);  
        String[] actualStrings = stringTracker.collectTracked(String[]::new);
        
        assertArrayEquals(intCycle, actualInts);
        assertArrayEquals(stringCycle, actualStrings);
    }
    
}
