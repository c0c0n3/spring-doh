package app.beans;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import app.core.cyclic.StdoutVisualizer;

/**
 * Extends {@link StdoutVisualizer} to make it a Spring bean.
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class StdoutVisualizerBean<T> extends StdoutVisualizer<T> {

}
