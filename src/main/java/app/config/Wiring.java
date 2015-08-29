package app.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import app.beans.StdoutVisualizerBean;

/**
 * Spring bean wiring configuration.
 */
@Configuration
@ComponentScan(basePackageClasses={StdoutVisualizerBean.class})
public class Wiring {

}
