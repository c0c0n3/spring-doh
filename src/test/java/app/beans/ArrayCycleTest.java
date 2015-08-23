package app.beans;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.Test;

import app.beans.Cycle.Position;

public class ArrayCycleTest {

    public static Cycle<Integer> newCycle(Integer...ts) {
        return new ArrayCycle<>(ts);
    }
    
    private void assertCycleUsingNext(Integer...ts) {
    	Cycle<Integer> target = newCycle(ts);
    	for(int k = 0; k < 3; ++k) {
            for (Integer t : ts) {
                assertThat(target.next(), is(t));
            }
        }
    }
    
    private void assertCycleUsingAdvance(Integer...ts) {
    	Cycle<Integer> target = newCycle(ts);
    	
    	Pair<Integer, Position> next = target.advance();
    	assertThat(next.fst(), is(ts[0]));
    	assertThat(next.snd(), is(Position.Start));
    	
    	for (int k = 1; k < ts.length; ++k) {
    		next = target.advance();
        	assertThat(next.fst(), is(ts[k]));
        	assertThat(next.snd(), is(Position.OnWay));
        }
    	
    	next = target.advance();
    	assertThat(next.fst(), is(ts[0]));
    	assertThat(next.snd(), is(Position.Start));
    }
    
    private void assertCycle(Integer...ts) {
        assertCycleUsingNext(ts);
        assertCycleUsingAdvance(ts);
    }
    
    @Test(expected= NullPointerException.class)
    public void nullCycleThows() {
        Cycle<Integer> target = newCycle((Integer[])null);
        target.next();
    }

    @Test(expected= ArithmeticException.class)
    public void emptyCycleThows() {
        Cycle<Integer> target = newCycle();
        target.next();
    }
    
    @Test
    public void singletonCycle() {
        assertCycle(10);
    }
    
    @Test
    public void binaryCycle() {
        assertCycle(10, 11);
    }
    
    @Test
    public void manyElementsCycle() {
        assertCycle(10, 11, 12, 13, 14);
    }
    
}
