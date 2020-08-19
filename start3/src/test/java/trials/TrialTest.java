package trials;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TrialTest {
    Trial trial1;
    Trial trial2;

    @Before
    public void init() {
        trial1 = new Trial("Vasya", 12, 13);
        trial2 = new Trial("Dima", 60, 41);
    }

    @Test
    public void constructorTest() {
        Trial trial = new Trial(trial2);
        assertEquals(trial1.toString(), new Trial("Vasya", 12, 13).toString());
        assertEquals(trial2.toString(), new Trial(trial).toString());
    }

    @Test
    public void getClassConstant() {
        assertEquals(50, Trial.getClassConstant());
    }

    @Test
    public void getAccount() {
        assertEquals("Vasya", trial1.getAccount());
        assertEquals("Dima", trial2.getAccount());
    }

    @Test
    public void getMark1() {
        assertEquals(12, trial1.getMark1());
        assertEquals(60, trial2.getMark1());
    }

    @Test
    public void getMark2() {
        assertEquals(13, trial1.getMark2());
        assertEquals(41, trial2.getMark2());
    }

    @Test
    public void testToString() {
        assertEquals("Vasya; His marks : 12; 13; trial is passed - false", trial1.toString());
        assertEquals("Dima; His marks : 60; 41; trial is passed - true", trial2.toString());
    }

    @Test
    public void fieldsToString() {
        assertEquals("Vasya; His marks : 12; 13", trial1.fieldsToString());
        assertEquals("Dima; His marks : 60; 41", trial2.fieldsToString());
    }

    @Test
    public void clearMarks() {
        trial1.clearMarks();
        trial2.clearMarks();
        assertEquals(0, trial1.getMark1());
        assertEquals(0, trial1.getMark2());
        assertEquals(0, trial2.getMark2());
        assertEquals(0, trial2.getMark2());
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