package trials;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class StrongTrialTest {
    StrongTrial trial1;
    StrongTrial trial2;
    StrongTrial trial3;

    @Before
    public void init() {
        trial1 = new StrongTrial("Vasya", 12, 13);
        trial2 = new StrongTrial("Dima", 60, 41);
        trial3 = new StrongTrial();
    }

    @Test
    public void constructorTest() {
        StrongTrial trial = new StrongTrial(trial2);
        assertEquals(trial1, new StrongTrial("Vasya", 12, 13));
        assertEquals(trial2, new StrongTrial(trial));
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