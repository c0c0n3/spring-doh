package integration.spring.lifecycle;

import static org.junit.Assert.*;

import java.util.stream.Stream;

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
import app.core.trips.TripsterGroup;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=TripsterGroupSingletonTest.class)
@ComponentScan(basePackageClasses={Wiring.class})
@ActiveProfiles({Profiles.HardCodedConfig})
public class TripsterGroupSingletonTest {

    @Autowired
    private ApplicationContext context;
    
    @Autowired
    private TripsterGroup<String> instance1;
    
    @Autowired
    private TripsterGroup<String> instance2;
    
    private void assertSameInstances(Object fst, Object snd) {
        assertNotNull(fst);
        assertNotNull(snd);
        assertTrue(fst == snd);
    }
    
    private void assertSameInstances(Object...xs) {
        if (xs.length < 2) return;
        
        Stream.of(xs)
              .forEach(x -> assertSameInstances(xs[0], x));
    }
    
    @Test
    public void sameObjectForEachAutowiredField() {
        assertSameInstances(instance1, instance2);
    }
    
    @Test
    @SuppressWarnings("unchecked")
    public void sameObjectReturnedByEachGetBeanCall() {
        TripsterGroup<String> fst = context.getBean(TripsterGroup.class);
        TripsterGroup<String> snd = context.getBean(TripsterGroup.class);
        
        assertSameInstances(fst, snd);
    }
    
    @Test
    public void visualizerSharedByAllGroupMembers() {
        Object[] xs = instance1
                     .members()
                     .map(t -> t.getVisualizer())
                     .toArray();
        
        assertSameInstances(xs);
    }
    
}
