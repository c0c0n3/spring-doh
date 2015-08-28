package app.core.cyclic;

import static org.junit.Assert.*;
import static util.Arrayz.array;

import java.util.stream.IntStream;

import org.junit.Test;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

import app.core.cyclic.Cycle.Position;
import util.Arrayz;
import util.Pair;

@RunWith(Theories.class)
public class ArrayCycleTest {

    @DataPoints
    public static Integer[][] cycles = new Integer[][] {
        array(10), array(10, 11), array(10, 11, 12, 13, 14)
    };
    
    @DataPoints
    public static Integer[] cycleRepeats = array(1, 2, 3);
    
    public static Cycle<Integer> newCycle(Integer...ts) {
        return new ArrayCycle<>(ts);
    }
    
    private void assertCycleUsingNext(int repeat, Integer...ts) {
        Cycle<Integer> target = newCycle(ts);
        
        Integer[] expected = Arrayz.op(Integer[]::new).cycle(repeat, ts);
        Integer[] actual = Cycles.collect(repeat * ts.length, target)
                          .toArray(new Integer[0]);
        
        assertArrayEquals(expected, actual);
    }
    
    private Pair<Integer, Position>[] buildExpectedForAdvance(Integer...ts) {
        Position[] ps = IntStream
                       .rangeClosed(0, ts.length)
                       .boxed()
                       .map(k -> k == 0 || k == ts.length ? 
                               Position.Start : Position.OnWay)
                       .toArray(Position[]::new);
        
        Integer[] rolledOverCycle = new Integer[ts.length + 1];
        System.arraycopy(ts, 0, rolledOverCycle, 0, ts.length);
        rolledOverCycle[ts.length] = ts[0];
        
        return Arrayz.zip(rolledOverCycle, ps);
        
        // NB we collect: start > ... > last > start
    }
    
    private Pair<Integer, Position>[] collectAdvance(Integer...ts) {
        Cycle<Integer> target = newCycle(ts);
        Pair<Integer, Position>[] ps = Arrayz.newPairs(ts.length + 1);
        
        for (int k = 0; k < ps.length; ++k) {
            ps[k] = target.advance();
        }
        return ps;
        
        // NB we collect: start > ... > last > start
    }
    
    private void assertCycleUsingAdvance(Integer...ts) {
        Pair<Integer, Position>[] expected = buildExpectedForAdvance(ts);
        Pair<Integer, Position>[] actual = collectAdvance(ts);

        assertArrayEquals(expected, actual);
    }
    
    @Theory
    public void assertCycle(int repeat, Integer...ts) {
        assertCycleUsingNext(repeat, ts);
        assertCycleUsingAdvance(ts);
    }

    @Test(expected = NullPointerException.class)
    public void nullCycleThows() {
        Cycle<Integer> target = newCycle((Integer[]) null);
        target.next();
    }

    @Test(expected = ArithmeticException.class)
    public void emptyCycleThows() {
        Cycle<Integer> target = newCycle();
        target.next();
    }
    
}
