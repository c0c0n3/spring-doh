package app.webq;

/**
 * Holds message data to put on the tripsters queue to request the display of 
 * what legs of the trip a tripster has traveled so far.
 */
public class ShowTripRequest {

    private String tripsterName;
    private int legsTraveled;
    
    public ShowTripRequest() {
        /* Jackson 2 will need an empty ctor to deserialize if no annotations
         * are provided. 
         * As an aside, Jackson 2 would still be able to deserialize correctly
         * if there were no annotations and no setters as long as an empty ctor
         * was provided.
         */
    }
    
    public ShowTripRequest(String tripsterName, int legsTraveled) {
        this.tripsterName = tripsterName;
        this.legsTraveled = legsTraveled;
    }
    
    public String getTripsterName() {
        return tripsterName;
    }
    
    public void setTripsterName(String tripsterName) {
        this.tripsterName = tripsterName;
    }
    
    public int getLegsTraveled() {
        return legsTraveled;
    }
    
    public void setLegsTraveled(int legsTraveled) {
        this.legsTraveled = legsTraveled;
    }

}
