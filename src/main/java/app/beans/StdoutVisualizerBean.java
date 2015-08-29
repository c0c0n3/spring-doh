package app.beans;

import org.springframework.stereotype.Component;

import app.core.cyclic.StdoutVisualizer;

/**
 * Extends {@link StdoutVisualizer} to make it a Spring bean.
 */
@Component
public class StdoutVisualizerBean<T> extends StdoutVisualizer<T> {

}
