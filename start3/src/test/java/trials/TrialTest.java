package trials;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TrialTest {
    Trial trial1;
    Trial trial2;
    Trial trial3;

    @Before
    public void init() {
        trial1 = new Trial("Vasya", 12, 13);
        trial2 = new Trial("Dima", 60, 41);
        trial3=new Trial();
    }

    @Test
    public void constructorTest() {
        Trial trial = new Trial(trial2);
        assertEquals(trial1.toString(), new Trial("Vasya", 12, 13).toString());
        assertEquals(trial2.toString(), new Trial(trial).toString());
    }
    @Test
    public void setAccountTest() {
        trial3.setAccount("Vasya");
        assertEquals("Vasya",trial3.getAccount());
    }
    @Test
    public void setMark1Test() {
        trial3.setMark1(15);
        assertEquals(15,trial3.getMark1());
    }
    @Test
    public void setMark2Test() {
        trial3.setMark2(15);
        assertEquals(15,trial3.getMark2());
    }
    @Test
    public void getAccountTest() {
        assertEquals("Vasya", trial1.getAccount());
        assertEquals("Dima", trial2.getAccount());
    }

    @Test
    public void getMark1Test() {
        assertEquals(12, trial1.getMark1());
        assertEquals(60, trial2.getMark1());
    }

    @Test
    public void getMark2Test() {
        assertEquals(13, trial1.getMark2());
        assertEquals(41, trial2.getMark2());
    }

    @Test
    public void toStringTest() {
        assertEquals("Vasya; His marks : 12; 13; trial is passed - false", trial1.toString());
        assertEquals("Dima; His marks : 60; 41; trial is passed - true", trial2.toString());
    }

    @Test
    public void fieldsToStringTest() {
        assertEquals("Vasya; His marks : 12; 13", trial1.fieldsToString());
        assertEquals("Dima; His marks : 60; 41", trial2.fieldsToString());
    }

    @Test
    public void clearMarksTest() {
        trial1.clearMarks();
        trial2.clearMarks();
        assertEquals(0, trial1.getMark1());
        assertEquals(0, trial1.getMark2());
        assertEquals(0, trial2.getMark2());
        assertEquals(0, trial2.getMark2());
    }

    @Test
    public void copyTest() {
        assertEquals(trial1.copy().toString(), trial1.toString());
        assertEquals(trial2.copy().toString(), trial2.toString());
    }

    @Test
    public void isPassedTest() {
        assertFalse(trial1.isPassed());
        assertTrue(trial2.isPassed());
    }
}