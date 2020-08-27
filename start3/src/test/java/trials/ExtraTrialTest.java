package trials;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ExtraTrialTest {
    ExtraTrial trial1;
    ExtraTrial trial2;
    ExtraTrial trial3;

    @Before
    public void init() {
        trial1 = new ExtraTrial("Vasya", 12, 13, 10);
        trial2 = new ExtraTrial("Dima", 60, 41, 88);
        trial3=new ExtraTrial();
    }

    @Test
    public void constructorTest() {
        ExtraTrial trial = new ExtraTrial(trial2);
        assertEquals(trial1.toString(), new ExtraTrial("Vasya", 12, 13, 10).toString());
        assertEquals(trial2.toString(), new ExtraTrial(trial).toString());
    }
    @Test
    public void setAccountTest() {
        trial3.setAccount("Vasya");
        assertEquals("Vasya",trial3.getAccount());
    }

    @Test
    public void setMark3Test() {
        trial3.setMark3(15);
        assertEquals(15,trial3.getMark3());
    }
    @Test
    public void getMark3Test() {
        assertEquals(88, trial2.getMark3());
    }

    @Test
    public void testToString() {
        assertEquals("Vasya; His marks : 12; 13; 10; trial is passed - false", trial1.toString());
        assertEquals("Dima; His marks : 60; 41; 88; trial is passed - true", trial2.toString());
    }

    @Test
    public void clearMarks() {
        trial1.clearMarks();
        trial2.clearMarks();
        assertEquals(0, trial1.getMark1());
        assertEquals(0, trial1.getMark2());
        assertEquals(0, trial2.getMark2());
        assertEquals(0, trial2.getMark2());
        assertEquals(0, trial1.getMark3());
        assertEquals(0, trial2.getMark3());
    }

    @Test
    public void copy() {
        assertEquals(trial1.copy().toString()
                , trial1.toString());
        assertEquals(trial2.copy().toString()
                , trial2.toString());
    }

    @Test
    public void isPassed() {
        assertFalse(trial1.isPassed());
        assertTrue(trial2.isPassed());
    }
}