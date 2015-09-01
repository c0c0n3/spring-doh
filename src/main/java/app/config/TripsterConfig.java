package app.config;

/**
 * Holds the data for a tripster as read from configuration.
 */
public class TripsterConfig {
    
    private String name;
    private String description;
    private String[] cycle;
    
    
    public TripsterConfig() { }
    
    public TripsterConfig(String name, String description, String[] cycle) {
        setName(name);
        setDescription(description);
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
}
