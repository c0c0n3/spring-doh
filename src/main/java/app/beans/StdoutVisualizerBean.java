package app.beans;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import app.config.Profiles;
import app.core.cyclic.StdoutVisualizer;

/**
 * Extends {@link StdoutVisualizer} to make it a Spring bean.
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Profile(Profiles.CliApp)
public class StdoutVisualizerBean<T> extends StdoutVisualizer<T> {

}
