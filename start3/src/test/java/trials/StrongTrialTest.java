package trials;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class StrongTrialTest {
    StrongTrial trial1;
    StrongTrial trial2;

    @Before
    public void init() {
        trial1 = new StrongTrial("Vasya", 12, 13);
        trial2 = new StrongTrial("Dima", 60, 41);
    }

    @Test
    public void constructorTest() {
        StrongTrial trial = new StrongTrial(trial2);
        assertEquals(trial1.toString(), new StrongTrial("Vasya", 12, 13).toString());
        assertEquals(trial2.toString(), new StrongTrial(trial).toString());
    }

    @Test
    public void getClassConstant() {
        assertEquals(50, StrongTrial.getClassConstant());
    }

    @Test
    public void copy() {
        assertEquals(trial1.copy().toString(), trial1.toString());
        assertEquals(trial2.copy().toString(), trial2.toString());
    }

    @Test
    public void isPassed() {
        assertFalse(trial1.isPassed());
        assertTrue(trial2.isPassed());
    }
}