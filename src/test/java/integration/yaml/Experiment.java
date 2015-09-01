package integration.yaml;

import static util.Arrayz.*;
import java.util.ArrayList;
import java.util.List;

import org.yaml.snakeyaml.Yaml;

import app.config.TripsterConfig;

public class Experiment {

    List<TripsterConfig> objects() {
        List<TripsterConfig> xs = new ArrayList<>();
        
        xs.add(new TripsterConfig("n1", "d1", array("1", "2")));
        xs.add(new TripsterConfig("n2", "d2", array("3", "4")));
        
        return xs;
    }
    
    String yaml() {
        return //"- !!app.config.TripsterConfig\n"
               "- cycle: ['1', '2']\n"
             + "  description: d1\n"
             + "  name: n1\n"
             //+ "- !!app.config.TripsterConfig\n"
             + "- cycle: ['3', '4']\n"
             + "  description: d2\n"
             + "  name: n2";
    }
    
    void run() {
        String yaml = new Yaml().dump(objects());
        
        System.out.println(yaml);
        
        @SuppressWarnings("unchecked")
        List<TripsterConfig> z = new Yaml().loadAs(yaml(), List.class);
        System.out.println(z);
    }
    
    public static void main(String[] args) {
        new Experiment().run();
    }
    
}
