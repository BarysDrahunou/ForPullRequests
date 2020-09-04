package myexceptions;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class WrongArgumentExceptionTest {
    WrongArgumentException wrongArgumentException1;
    WrongArgumentException wrongArgumentException2;
    @Before
    public void init() {
        wrongArgumentException1 = new WrongArgumentException("Problem value"
                , "my problem value");
        wrongArgumentException2 = new WrongArgumentException("There is a problem value"
                , "There is a description",new IOException("Fail with reading"));
    }

    @Test
    public void testGetException() {
        assertEquals(new IllegalArgumentException("Problem value - my problem value").getMessage()
                , wrongArgumentException1.getMessage());
        assertEquals(new IllegalArgumentException("There is a problem value - There is a description").getMessage()
                , wrongArgumentException2.getMessage());
    }

    @Test
    public void testGetProblemValue() {
        assertEquals("Problem value", wrongArgumentException1.getProblemValue());
        assertEquals("There is a problem value", wrongArgumentException2.getProblemValue());
    }

    @Test
    public void getMessage() {
        assertEquals("Problem value - my problem value"
                , wrongArgumentException1.getMessage());
        assertEquals("There is a problem value - There is a description"
                , wrongArgumentException2.getMessage());
    }
}