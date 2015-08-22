package app.beans;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.Test;

public class ArrayCycleTest {

    private Cycle<Integer> newCycle(Integer...ts) {
        return new ArrayCycle<>(ts);
    }
    
    private void assertCycle(Integer...ts) {
        Cycle<Integer> target = newCycle(ts);
        for(int k = 0; k < 3; ++k) {
            for (Integer t : ts) {
                assertThat(target.next(), is(t));
            }
        }
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
