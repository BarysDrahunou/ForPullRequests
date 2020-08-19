package trials;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class LightTrialTest {
    LightTrial trial1;
    LightTrial trial2;

    @Before
    public void init() {
        trial1 = new LightTrial("Vasya", 12, 13);
        trial2 = new LightTrial("Dima", 60, 41);
    }

    @Test
    public void constructorTest() {
        LightTrial trial = new LightTrial(trial2);
        assertEquals(trial1.toString(), new LightTrial("Vasya", 12, 13).toString());
        assertEquals(trial2.toString(), new LightTrial(trial).toString());
    }

    @Test
    public void getClassConstantForTest1() {

        assertEquals(10, LightTrial.getClassConstantForTest1());
    }

    @Test
    public void getClassConstantForTest2() {

        assertEquals(20, LightTrial.getClassConstantForTest2());
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