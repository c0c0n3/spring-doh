package integration.spring.config;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import util.config.ConfigProvider;
import app.config.Profiles;
import app.config.Wiring;
import app.config.data.TripstersYmlFile;
import app.config.items.TripsterConfig;
import app.config.providers.FileTripsters;
import app.config.providers.PriorityConfigProvider;
import app.run.RunnableApp;
import app.run.TripstersYmlGen;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=Wiring.class)
@ActiveProfiles({Profiles.ConfigFile})
public class TripstersFileConfigTest extends FileConfigTest<TripsterConfig> {

    @Autowired
    private FileTripsters configProvider;
    
    @Override
    protected PriorityConfigProvider<TripsterConfig> getConfigProvider() {
        return configProvider;
    }
    
    @Override
    protected RunnableApp getFileGenerator() {
        return new TripstersYmlGen();
    }
    
    @Override
    protected ConfigProvider<TripsterConfig> getFileContents() {
        return new TripstersYmlFile();
    }
    
}
