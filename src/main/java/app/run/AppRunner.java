package app.run;

import java.util.Arrays;
import java.util.List;

/**
 * Runs one of our silly applications in this package.
 */
public class AppRunner {

    private void usage() {
        System.out.println("To run any of the available apps:");
        System.out.println();
        System.out.println("\tjava -jar build/libs/spring-doh-<n>.jar <fqcn> [args]");
        System.out.println();
        System.out.println("where:");
        System.out.println("\t'n' is the version number of the spring-doh jar");
        System.out.println("\t'fqcn' is the fully qualified class name of the app to run;");
        System.out.println("\t       it is expected to implement RunnableApp");
        System.out.println("\t'args' are any arguments to pass to the app");
    }
    
    private List<String> buildAppArgs(String[] args) {
        return Arrays.asList(args).subList(1, args.length);
    }
    
    private void invoke(String[] args) {
        try {
            RunnableApp app = (RunnableApp) Class.forName(args[0]).newInstance();
            List<String> appArgs = buildAppArgs(args);
            app.run(appArgs);
        } catch (Exception e) {
            // just print; user's smart enough to figure out what went wrong...
            e.printStackTrace();
        }
    }
    
    /**
     * Runs the app with the given command line arguments.
     * @param args the arguments vector as given on the command line. 
     */
    public void launch(String[] args) {
        if (args == null || args.length == 0) {
            usage();
        }
        else {
            invoke(args);
        }
    }
    
}
