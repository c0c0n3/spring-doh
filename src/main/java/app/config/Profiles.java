package app.config;

/**
 * Enumerates the profile constants used to select configuration profiles.
 */
public class Profiles {
    // NB why not use an enum? Because it won't work with @Profile annotations. 
    // In fact the value of the annotation needs to be a constant, so something
    // like @Profile(ProfilesEnum.SomeEnumValue.name()) will make the compiler
    // puke up.

    /**
     * Hard-coded data to use if no external config is provided or for testing.
     */
    public static final String HardCodedConfig = "HardCodedConfig";
    
    /**
     * Configuration data from file.
     */
    public static final String ConfigFile = "ConfigFile";
    
    /**
     * Beans specific to Web apps.
     */
    public static final String WebApp = "WebApp";
    
   /**
    * Beans that are *not* specific to Web apps.
    */
   public static final String NotWebApp = "!WebApp";
   
   /**
    * Beans specific to the Web app embedding a queue.
    */
   public static final String WebQ = "WebQ";
    
}
