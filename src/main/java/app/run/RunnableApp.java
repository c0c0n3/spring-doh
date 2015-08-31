package app.run;

import java.util.List;

/**
 * All our silly apps in this package implement this interface so we can easily
 * run any of them; also they all are expected to have a parameter-less ctor.
 * The command line to run any of them is supposed to look like:
 * <pre>
 * java -jar build/libs/spring-doh-0.1.0.jar app.run.ChosenApp any app args
 * </pre>
 * where {@code app.run.ChosenApp} is the fully qualified name of a class
 * implementing this interface and "{@code any app args}" are, well, exactly 
 * what you think: any arguments specific to the app to run.
 * 
 * @see AppRunner
 */
public interface RunnableApp {

    /**
     * Runs the app with the given command line arguments.
     * These are the arguments specific to the app, that is what follows the
     * fully qualified class name of the app on the command line.
     * @param appArgs the app arguments as given on the command line. 
     */
    void run(List<String> appArgs);
    
}
