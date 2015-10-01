package app.config.data;

import java.util.function.Supplier;

import app.config.items.UndertowConfig;

/**
 * The data that goes into 'config/undertow.yml'.
 */
public class UndertowYmlFile implements Supplier<UndertowConfig> {

    public static final UndertowConfig contents = new UndertowYmlFile().get();
    
    @Override
    public UndertowConfig get() {
        UndertowConfig cfg = new UndertowConfig();
        cfg.setPort(8080);
        
        return cfg;
    }

}
