package app.config;

import java.util.Arrays;
import java.util.Objects;

/**
 * Holds the data for a tripster as read from configuration.
 */
public class TripsterConfig {
    // NB this has to be a Java Bean (i.e. getters/setters, no args ctor) to
    // be (de-)serialized painlessly by SnakeYaml.
    
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
    
    @Override
    public int hashCode() {
        return toString().hashCode();
    }
    
    @Override
    public boolean equals(Object other) {
        if (other instanceof TripsterConfig) {
            return Objects.equals(other.toString(), this.toString());
        }
        return false;
    }
    
    @Override
    public String toString() {
        String cs = Arrays.toString(cycle);
        return String.format("%s | %s | %s", name, description, cs);
    }
    
}
