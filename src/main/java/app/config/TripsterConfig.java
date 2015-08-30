package app.config;

import static util.Arrayz.array;

import java.util.stream.IntStream;

/**
 * Holds the configuration data for a tripster as read from file.
 */
public class TripsterConfig {

    /**
     * Ventures in numberland.
     * @return
     */
    private static TripsterConfig gauss() {  // TODO move to xml config
        String[] intsMod5 = IntStream
                           .range(0, 5)
                           .boxed()
                           .map(i -> i + " (mod 5)")
                           .toArray(String[]::new);
        
        return new TripsterConfig("gauss", intsMod5);
    }
    
    /**
     * Tours South Africa.
     * @return
     */
    private static TripsterConfig hipster() {  // TODO move to xml config
        String[] trip = array("Cape Town", "Knysna", "Durbs", "Jozi");
        return new TripsterConfig("hipster", trip);
    }
    
    /**
     * Reads the happy bunch of tripsters from configuration.
     * @return all configured tripsters; never {@code null}.
     */
    public static TripsterConfig[] readConfig() {
        // TODO read from xml config file instead
        return array(gauss(), hipster());
    }
    
    
    private String name;
    private String[] cycle;
    
    public TripsterConfig() { }
    
    public TripsterConfig(String name, String[] cycle) {
        setName(name);
        setCycle(cycle);
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String[] getCycle() {
        return cycle;
    }
    
    public void setCycle(String[] cycle) {
        this.cycle = cycle;
    }
    
}
