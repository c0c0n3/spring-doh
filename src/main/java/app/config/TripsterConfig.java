package app.config;

/**
 * Holds the data for a tripster as read from configuration.
 */
public class TripsterConfig {
    
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
