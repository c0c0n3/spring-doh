package util.spring.io;

import static util.Streams.pruneNull;

import java.util.Optional;

import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

/**
 * Extends {@link ResourceLoader} to cater for a resource that may be available
 * from multiple locations (loci).
 * The implementation will use some strategy to determine from which of the
 * possible locations the resource should be loaded.
 */
public interface LociResourceLoader extends ResourceLoader {

    /**
     * Same as {@link #getResource(String) getResource} with a single argument
     * but applies a strategy to determine from which of the given locations 
     * the resource should be loaded.
     * @param loci the possible locations of the resource.
     * @return a handle for the resource at the location selected out of the
     * possible choices; note that, as with any other {@link ResourceLoader},
     * obtaining a handle does not imply the {@link Resource#exists() existence}
     * of the resource. It may turn out that none of the given locations is
     * suitable to load the resource in which case an {@link Optional#empty()
     * empty} optional is returned.
     * @deprecated use the {@link #selectResource(ResourceLocation[]) other} 
     * version of this method instead. 
     */
    Optional<Resource> selectResource(String...loci);
    
    /**
     * Same as {@link #getResource(String) getResource} with a single argument
     * but applies a strategy to determine from which of the given locations 
     * the resource should be loaded.
     * @param loci the possible locations of the resource.
     * @return a handle for the resource at the location selected out of the
     * possible choices; note that, as with any other {@link ResourceLoader},
     * obtaining a handle does not imply the {@link Resource#exists() existence}
     * of the resource. It may turn out that none of the given locations is
     * suitable to load the resource in which case an {@link Optional#empty()
     * empty} optional is returned. 
     */
    default Optional<Resource> selectResource(ResourceLocation...loci) {
        return selectResource(
                pruneNull(loci)
                .map(ResourceLocation::get)
                .toArray(String[]::new));
    }
    /* TODO reimplement FifoResourceLoaderAdapter to use ResourceLocation,
    /* then update the FifoResourceLoaderAdapterTest accordingly.
     */
}
