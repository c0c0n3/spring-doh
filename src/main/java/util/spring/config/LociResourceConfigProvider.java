package util.spring.config;

import static java.util.Objects.requireNonNull;

import java.util.stream.Stream;

import util.config.ConfigProvider;
import util.spring.io.LociResourceLoader;
import util.spring.io.ResourceLocation;
import util.spring.io.ResourceReader;

/**
 * Reads configuration items from a resource that may be located in more than
 * one place.
 */
public class LociResourceConfigProvider<T> implements ConfigProvider<T> {

    private final LociResourceLoader resourceLoader;
    private final ResourceLocation[] loci;
    private final ResourceReader<T> converter;
    
    /**
     * Creates a new instance to read configuration items from a resource that
     * may be located in the specified locations.
     * @param resourceLoader determines where to read the resource from, among
     * the candidate locations.
     * @param converter transforms the resource data stream into a steam of
     * {@code T}'s.
     * @param loci the resource candidate locations.
     * @throws NullPointerException if the loader or converter is {@code null}.
     */
    public LociResourceConfigProvider(LociResourceLoader resourceLoader, 
                                      ResourceReader<T> converter,
                                      ResourceLocation...loci) {
        requireNonNull(resourceLoader, "resourceLoader");
        requireNonNull(converter, "converter");
        
        this.resourceLoader = resourceLoader;
        this.converter = converter;
        this.loci = loci;
    }
    
    @Override
    public Stream<T> readConfig() throws Exception {
        return resourceLoader
                    .selectResource(loci)
                    .map(converter::readResource)
                    .orElse(Stream.empty());  // (*)
    }
    /* (*) readResource should never return null, but you better look over 
     * your shoulder in Java land...
     */

}
