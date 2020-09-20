package trials;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class LightTrialTest {

    LightTrial trial1;
    LightTrial trial2;
    LightTrial trial3;

    @Before
    public void init() {
        trial1 = new LightTrial("Vasya", 12, 13);
        trial2 = new LightTrial("Dima", 60, 41);
        trial3 = new LightTrial();
    }

    @Test
    public void constructorTest() {
        LightTrial trial = new LightTrial(trial2);
        assertEquals(trial1, new LightTrial("Vasya", 12, 13));
        assertEquals(trial2, new LightTrial(trial));
    }

    @Test
    public void copy() {
        assertEquals(trial1.getCopy(), trial1);
        assertEquals(trial2.getCopy(), trial2);
        assertNotSame(trial1.getCopy(), trial1);
        assertNotSame(trial2.getCopy(), trial2);
    }

    @Test
    public void isPassed() {
        assertFalse(trial1.isPassed());
        assertTrue(trial2.isPassed());
    }
}