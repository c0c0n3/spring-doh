package app.webq;

import static util.Exceptions.unchecked;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import app.core.trips.TripsterSpotter;


/**
 * Reads a message from the tripsters queue and prints out what legs of the trip 
 * a tripster has traveled so far.
 */
@Component
public class TripsterQConsumer {
    
    public static String listnerMethodName() {
        return unchecked(
                () -> TripsterQConsumer
                        .class
                        .getMethod("consume", ShowTripRequest.class)
                        .getName())
                        .get();
    }
    /* bombs out if we change the consume method, so it's safer to use with
     * MessageListenerAdapter#setDefaultListenerMethod instead of just passing
     * in a plain string "consume".
     */
    
    
    @Autowired
    @Qualifier("tripsterSpotterWithStdoutViz")
    private TripsterSpotter<String> spotter;
    // NB will be shared across requests (!) as spotter is a singleton.
    
    public void consume(ShowTripRequest r) {
        spotter.showWhereIs(r.getTripsterName(), r.getLegsTraveled());
    }
    
}
