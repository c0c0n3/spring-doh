package app.beans;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import app.config.Profiles;
import app.core.cyclic.Cycle;
import app.core.cyclic.CycleVisualizer;
import app.core.cyclic.TextVisualizer;

/**
 * A cycle visualizer bean that writes to a string buffer.  
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Profile(Profiles.WebApp)
public class StringVisualizerBean<T> implements CycleVisualizer<T> {

    private StringWriter buffer;
    
    @Override
    public void show(Cycle<T> target, int length) {
        buffer = new StringWriter();
        PrintWriter out = new PrintWriter(buffer);
        CycleVisualizer<T> delegate = new TextVisualizer<>(out);
        
        delegate.show(target, length);
    }
    
    /** 
     * @return the cycle path rendered by the most recent call to {@link 
     * #show(Cycle, int) show} method or the empty string if said method
     * has never been called. 
     */
    public String getShown() {
        return buffer == null ? "" : buffer.toString();
    }
    
}
