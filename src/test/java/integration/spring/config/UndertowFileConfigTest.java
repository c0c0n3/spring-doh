package integration.spring.config;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import util.config.ConfigProvider;
import app.config.Profiles;
import app.config.Wiring;
import app.config.data.UndertowYmlFile;
import app.config.items.UndertowConfig;
import app.config.providers.PriorityConfigProvider;
import app.config.providers.UndertowConfigProvider;
import app.run.RunnableApp;
import app.run.UndertowYmlGen;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=Wiring.class)
@ActiveProfiles({Profiles.ConfigFile})
public class UndertowFileConfigTest extends FileConfigTest<UndertowConfig> {

    @Autowired
    private UndertowConfigProvider configProvider;
    
    @Override
    protected PriorityConfigProvider<UndertowConfig> getConfigProvider() {
        return configProvider;
    }

    @Override
    protected RunnableApp getFileGenerator() {
        return new UndertowYmlGen();
    }

    @Override
    protected ConfigProvider<UndertowConfig> getFileContents() {
        return new UndertowYmlFile();
    }

}
