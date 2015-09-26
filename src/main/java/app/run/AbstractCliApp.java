package app.run;

import java.util.List;

import app.core.trips.TripsterSpotter;

/**
 * Convenience template class to factor out logic common to the running of the
 * core functionality from the command line.
 */
public abstract class AbstractCliApp implements RunnableApp {

    /**
     * Prints out required arguments.
     */
    protected void usage() {
        System.out.println("Required app arguments: <tripster> <n>");
        System.out.println("where n is an integer and tripster is one of:");
        System.out.println(spotter().describeTripsters());
    }
    
    /**
     * The guy who's keeping an eye on this app's happy bunch of tripsties.
     * @return the configured tripster spotter.
     */
    protected abstract <T> TripsterSpotter<T> spotter();
    
    /**
     * Runs the app with the CLI-supplied arguments.
     * @param tripsterName the name of the tripster.
     * @param legsTraveled hops away from the tripster's home. 
     */
    protected void runApp(String tripsterName, int legsTraveled) {
        boolean found = spotter().showWhereIs(tripsterName, legsTraveled);
        if (!found) {
            System.err.println("No such tripster: " + tripsterName);
        }
    }
    
    @Override
    public void run(List<String> args) {
        if (args == null || args.size() < 2) {
            usage();
        }
        else {
            String tripsterName = args.get(0);
            int legsTraveled = Integer.parseInt(args.get(1));
            // let exceptions bubble up as user can figure out what that means...
            
            runApp(tripsterName, legsTraveled);
        }
    }
    
}
