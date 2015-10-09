package app.config.items;

import org.hornetq.jms.server.config.JMSQueueConfiguration;

/**
 * Holds the data to define a HornetQ queue.
 */
public class TripstersQConfig implements JMSQueueConfiguration {
    // NB this has to be a Java Bean (i.e. getters/setters, no args ctor) to
    // be (de-)serialized painlessly by SnakeYaml.
    
    private String name;
    private String selector;
    private boolean durable;
    private String[] bindings;
    
    @Override
    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    @Override
    public String getSelector() {
        return this.selector;
    }
    
    public void setSelector(String selector) {
        this.selector = selector;
    }
    
    @Override
    public boolean isDurable() {
        return this.durable;
    }

    public void setDurable(boolean durable) {
        this.durable = durable;
    }
    
    @Override
    public String[] getBindings() {
        return this.bindings;
    }

    public void setBindings(String[] bindings) {
        this.bindings = bindings;
    }

}
