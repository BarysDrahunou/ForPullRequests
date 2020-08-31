package myexceptions;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class WrongArgumentExceptionTest {
    WrongArgumentException wrongArgumentException;
    @Before
    public void init() {
        wrongArgumentException = new WrongArgumentException("Problem value"
                , "my problem value");
    }

    @Test
    public void testGetException() {
        assertEquals(new IllegalArgumentException("Problem value").getMessage()
                , wrongArgumentException.getException().getMessage());
        assertEquals(IllegalArgumentException.class
                , wrongArgumentException.getException().getClass());
    }

    @Test
    public void testGetProblemValue() {
        assertEquals("my problem value", wrongArgumentException.getProblemValue());
    }

    @Test
    public void testToString1() {
        assertEquals("my problem value - Problem value"
                , wrongArgumentException.toString());
    }
}