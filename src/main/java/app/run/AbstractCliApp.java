package app.run;

import java.util.List;

import util.Pair;
import app.core.trips.TripsterGroup;

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
        tripsters()
            .describe()
            .forEach(this::printTripster);
    }
    
    protected void printTripster(Pair<String, String> p) {
        String name = p.fst(), desc = p.snd();
        String line = String.format("\t'%s' (%s)", name, desc);
        System.out.println(line);
    }
    
    /**
     * The app happy bunch of tripsties.
     * @return the configured group of tripsters.
     */
    protected abstract <T> TripsterGroup<T> tripsters();
    
    /**
     * Runs the app with the CLI-supplied arguments.
     * @param tripsterName the name of the tripster.
     * @param legsTraveled hops away from the tripster's home. 
     */
    protected abstract void runApp(String tripsterName, int legsTraveled);
    
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
