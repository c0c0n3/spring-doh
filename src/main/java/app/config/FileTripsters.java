package app.config;

import java.io.InputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import util.lambda.FunctionE;
import util.spring.io.FifoResourceLoaderAdapter;


/**
 * Reads tripster configuration from a YAML file, falling back to hard-coded
 * configuration if no file is avaliable.
 * This provider will first try to read the file from the {@link #PwdConfig
 * current directory}; failing that, it will try to find the file in the
 * {@link #ClasspathConfig classpath}, falling back to hard-code config if
 * not found.
 */
@Component
@Profile(Profiles.ConfigFile)
public class FileTripsters implements ConfigProvider<List<TripsterConfig>> {

    /**
     * Location of the YAML file in the classpath.
     */
    public static final String ClasspathConfig = 
            ResourceLoader.CLASSPATH_URL_PREFIX + "/config/tripsters.yml";
    
    /**
     * Location of the YAML file in the current directory.
     */
    public static final String PwdConfig = "file:./tripsters.yml";
    
    @Autowired
    private ResourceLoader resourceLoader;
    
    /**
     * Reads the happy bunch of tripsters from configuration.
     * @return all configured tripsters; never {@code null}.
     */
    @Override
    public List<TripsterConfig> readConfig() throws Exception {
        return readConfig(PwdConfig, ClasspathConfig);
    }
    
    /**
     * Reads config from the first available out of the specified locations,
     * falling back to hard-coded config if no file is available.
     */
    public List<TripsterConfig> readConfig(String...loci) throws Exception {
        TripsterConfigYaml reader = new TripsterConfigYaml();
        HardCodedTripsters fallback = new HardCodedTripsters();
        FunctionE<Resource, InputStream> getResourceStreamOrThrowIoE = 
                                                    Resource::getInputStream;
        
        return new FifoResourceLoaderAdapter(resourceLoader)
                    .selectResource(loci)
                    .map(getResourceStreamOrThrowIoE)  // (*) see note below
                    .map(reader::fromYaml)
                    .orElse(fallback.defaultReadConfig());
    }
}
/* NOTE. 
 * replacing: .map(getResourceStreamOrThrowIoE)
 *      with: .map(unchecked(Resource::getInputStream))
 * or for that matter
     FunctionE<Resource, InputStream> getResourceStreamOrThrowIoE = 
                                                Resource::getInputStream;
 * with
     Function<Resource, InputStream> getResourceStreamOrThrowIoE = 
                                    unchecked(Resource::getInputStream);
                                    
 * will confuse the hell out of the eclipse compiler when trying to do type 
 * inference, so it won't compile in eclipse, but it will in OpenJDK 1.8. 
 * Looks like I'm not alone tho as Java's typsie infirmity has caused some
 * headaches to the surgeons over here too:
 * - https://bugs.eclipse.org/bugs/show_bug.cgi?id=461004 
 */