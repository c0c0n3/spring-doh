package app.core;

import static java.util.Objects.requireNonNull;

import java.util.stream.Stream;

import util.Arrayz;
import util.Pair;
import app.core.Cycle.Position;

/**
 * 
 *
 */
public enum CyclePath {
    
    /* the idea is to make the position more descriptive in the path collected
     * by iterating a cycle. e.g. assume 0 is the cycle starting point and
     * you iterated as below:
     * 
     * cycle:  0 -> 1 -> 2  (-> 0 = starting point)
     * 
     * iterated: 0 -> 1 -> 2 -> 0
     * maps to:  Home -> OnWay -> OnWay -> End 
     * 
     * iterated: 0 -> 1 -> 2 -> 0 -> 1
     * maps to:  Home -> OnWay -> OnWay -> BackHome -> OnWay
     * 
     * iterated: 0 -> 1 -> 2 -> 0 -> 1 -> 2 -> 0
     * maps to:  Home -> OnWay -> OnWay -> BackHome -> OnWay -> OnWay -> End
     * 
     * ...but I think this is actually a crap idea so I'll probably nuke this
     * class...
     */
    
    /**
     * Used when the first element of the path is at the {@link Position#Start 
     * start} position in the cycle.
     */
    Home, 
    
    /**
     * Used for any path element that is at an {@link Position#OnWay in-between}
     * position in the cycle. 
     */
    OnWay, 
    
    /**
     * Used when a path element is at the {@link Position#Start start} position
     * in the cycle but it's neither the first nor the last element of the path. 
     */
    BackHome, 
    
    /**
     * Used when the last element of the path is at the {@link Position#Start 
     * start} position in the cycle and the path has at least length two.
     */
    End;
    
    /**
     * Converts 
     * @param iteratedCycle
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T>
    Pair<T, CyclePath>[] from(Stream<Pair<T, Position>> iteratedCycle) {
        requireNonNull(iteratedCycle, "iteratedCycle");
        
        Pair<T, Position>[] path = iteratedCycle.toArray(Arrayz::newPairs);
        
        return Arrayz.op(sz -> (Pair<T, CyclePath>[]) new Pair[sz])  // (*)
                     .map((i, p) -> convert(i, path.length, p), path);
    }
    // (*) using Arrayz::newPairs wouldn't work, so I'm inlining the call.
    // This is surprising as the call above it works just fine.
    // If somebody could explain to me why I'll be forever grateful.

    private static <T> 
    Pair<T, CyclePath> convert(Integer ix, int pathLength, Pair<T, Position> p) {
        return new Pair<>(p.fst(), mapPosition(ix, pathLength, p.snd()));
    }
    
    private static 
    CyclePath mapPosition(int index, int pathLength, Position where) {
        switch (where) {
        case OnWay:
            return OnWay;
        case Start:
            if (index == 0) return Home;
            if (0 < index && index < pathLength - 1) return BackHome;
            return End;
        }
        return null;  // keep compiler happy as it can't do case analysis...
    }
    
}
