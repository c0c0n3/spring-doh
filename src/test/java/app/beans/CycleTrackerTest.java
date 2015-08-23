package app.beans;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Test;

public class CycleTrackerTest {

	public static CycleTracker<Integer> newCycleTracker(Integer...ts) {
		Cycle<Integer> target = ArrayCycleTest.newCycle(ts);
		return new CycleTracker<>(target);
	}
	
	private void assertIterated(Integer...ts) {
		CycleTracker<Integer> target = newCycleTracker(ts);
		assertThat(target.iteratedSoFar().toArray().length, is(0));
		
		for(int k = 0; k < ts.length; ++k) {
			Integer[] expected = Arrays.copyOfRange(ts, 0, k+1);
            
			target.next();
            Integer[] actual = target
            				 .iteratedSoFar()
            				 .map(Pair::fst)
            				 .toArray(Integer[]::new);
			
            assertArrayEquals(expected, actual);
        }
	}
	
	@Test
    public void emptyCycle() {
		assertIterated();
    }
	
	@Test
    public void singletonCycle() {
		assertIterated(10);
    }
    
    @Test
    public void binaryCycle() {
    	assertIterated(10, 11);
    }
    
    @Test
    public void manyElementsCycle() {
    	assertIterated(10, 11, 12, 13, 14);
    }

}
