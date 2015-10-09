package app.config.data;

import static util.Arrayz.array;

import java.util.stream.Stream;

import app.config.items.TripstersQConfig;
import util.config.ConfigProvider;

public class DefaultTripstersQ implements ConfigProvider<TripstersQConfig>{

    @Override
    public Stream<TripstersQConfig> readConfig() throws Exception {
        TripstersQConfig q = new TripstersQConfig();
        q.setName("tripsters");
        q.setDurable(false);
        q.setBindings(array());  // (*)
        
        return Stream.of(q);
    }
    /* (*) these would be the JNDI names for the queue; it's a required 
     * config item (i.e. can't be null) but we're not gonna use JNDI, so
     * we return an empty array.
     */
}
