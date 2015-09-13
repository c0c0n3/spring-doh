package app.core.trips;

import static java.util.Objects.requireNonNull;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Stream;

import util.Pair;

/**
 * A bunch of happy tripsters going places.
 */
public class TripsterGroup<T> {

    private Map<String, Tripster<T>> tripsters;
    
    private Map<String, Tripster<T>> buildMap(List<Tripster<T>> ts) {
        requireNonNull(ts, "tripsters");
        
        Collector<Tripster<T>, ?, Map<String, Tripster<T>>> putTripster = 
                toMap(Tripster<T>::getName, identity(), (oldT, newT) -> newT);
        
        return ts.stream().filter(t -> t != null).collect(putTripster);
    }
    
    /**
     * Creates a new group of tripsters.
     * @param tripsters the members of this group.
     */
    public TripsterGroup(List<Tripster<T>> tripsters) {
        this.tripsters = buildMap(tripsters);
    }
    
    /**
     * Looks up tripsters by their name. 
     * @param tripsterName the tripster's name.
     * @return a tripster if one with the given name was found; absent otherwise.
     */
    public Optional<Tripster<T>> lookup(String tripsterName) {
        return Optional.ofNullable(tripsters.get(tripsterName));
    }
    
    /**
     * Lists the members of this group.
     * @return the group members.
     */
    public Stream<Tripster<T>> members() {
        return tripsters
                .entrySet()
                .stream()
                .map(e -> e.getValue());
    }
    
    /**
     * Lists the name and description of each member of this group.
     * @return a list of pairs {@code (name, description)} ordered by name.
     */
    public Stream<Pair<String, String>> describe() {
        return tripsters
              .entrySet()
              .stream()
              .map(e -> new Pair<>(e.getKey(), e.getValue().getDescription()))
              .sorted((p, q) -> p.fst().compareTo(q.fst()));
    }
    // NOTE: (1) e.getValue != null as buildMap doesn't add null values
    //  and  (2) map keys can't be null
}
