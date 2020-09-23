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
        assertEquals(trial1, new ExtraTrial("Vasya", 12, 13, 10));
        assertEquals(trial2, new ExtraTrial(trial));
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

    @Test
    public void testEquals() {
        assertEquals(new ExtraTrial("Vasya", 12, 13, 10), trial1);
        assertNotEquals(new ExtraTrial("Egor", 60, 41, 88), trial2);
        assertNotEquals(new ExtraTrial("Dima", 61, 41, 88), trial2);
        assertNotEquals(new ExtraTrial("Dima", 60, 41, 89), trial2);
    }

    @Test
    public void testHashCode() {
        assertEquals(new ExtraTrial("Vasya", 12, 13, 10).hashCode(), trial1.hashCode());
        assertNotEquals(new ExtraTrial("Egor", 60, 41, 88).hashCode(), trial2.hashCode());
        assertNotEquals(new ExtraTrial("Dima", 61, 41, 88).hashCode(), trial2.hashCode());
        assertNotEquals(new ExtraTrial("Dima", 60, 41, 89).hashCode(), trial2.hashCode());
    }
}