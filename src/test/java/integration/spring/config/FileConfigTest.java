package integration.spring.config;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.OutputCapture;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import app.config.Profiles;
import app.config.Wiring;
import app.config.data.DefaultTripsters;
import app.config.data.TripstersYmlFile;
import app.config.providers.FileTripsters;
import app.run.TripstersYmlGen;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=Wiring.class)
@ActiveProfiles({Profiles.ConfigFile})
public class FileConfigTest {
    
    @Rule
    public OutputCapture generatedConfig = new OutputCapture();
    
    @Rule
    public TemporaryFolder configDirUnderPwd = new TemporaryFolder(new File("./"));
    
    @Autowired
    private FileTripsters configProvider;
    
    
    private String generateTripstersYaml() {
        TripstersYmlGen app = new TripstersYmlGen();
        app.run(null);
        
        String fileContents = generatedConfig.toString();
        return fileContents;
    }
    
    private String writeTripstersYml() throws IOException {
        String fileContents = generateTripstersYaml();
        
        String fileName = "tripsters.yml";
        File tripstersYml = configDirUnderPwd.newFile(fileName);
        PrintWriter out = new PrintWriter(tripstersYml);
        out.print(fileContents);
        out.close();
        
        String configDirName = configDirUnderPwd.getRoot().getName();
        return "file:./" + configDirName + "/" + fileName;
    }
    
    @Test
    public void readConfigFileFromConfigDirUnderPwd() throws Exception {
        String pathRelativeToPwd = writeTripstersYml();
        
        Object[] actual = configProvider.readConfig(pathRelativeToPwd)
                                        .toArray();
        Object[] expected = TripstersYmlFile.tripsters.toArray();
        
        assertArrayEquals(expected, actual);
    }
    
    @Test
    public void defaultToHardCodedConfigIfNoOtherAvailable() throws Exception {
        Object[] actual = configProvider.readConfig("some", "nonsense")
                                        .toArray();
        Object[] expected = DefaultTripsters.tripsters.toArray();
        
        assertArrayEquals(expected, actual);
    }
    
}
