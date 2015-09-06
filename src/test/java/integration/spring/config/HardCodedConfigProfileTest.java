package integration.spring.config;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import app.config.ConfigProvider;
import app.config.Profiles;
import app.config.TripsterConfig;
import app.config.Wiring;
import app.config.data.DefaultTripsters;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=Wiring.class)
@ActiveProfiles(Profiles.HardCodedConfig)
public class HardCodedConfigProfileTest {

    @Autowired
    private ConfigProvider<List<TripsterConfig>> configProvider;
    
    @Test
    public void defaultTripstersWhenHardCodedConfigProfile() throws Exception {
        Object[] actual = configProvider.readConfig().toArray();
        Object[] expected = DefaultTripsters.tripsters.toArray();
        
        assertArrayEquals(expected, actual);
    }
    
}
