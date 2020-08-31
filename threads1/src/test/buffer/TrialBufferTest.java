package buffer;

import org.junit.Before;
import org.junit.Test;
import trials.Trial;

import static org.junit.Assert.*;

public class TrialBufferTest {
    TrialBuffer trialBuffer;
    Trial trial;

    @Before
    public void init() {
        trialBuffer = new TrialBuffer();
        trial=new Trial("Igor",22,33);
    }

    @Test
    public void putAndTakeTrialTest() {
        trialBuffer.putTrial(trial);
        Trial newTrial=trialBuffer.takeTrial();
        assertEquals(newTrial, trial);
        trialBuffer.putTrial(new Trial("Max",22,33));
        assertNotEquals(trialBuffer.takeTrial(),trial);
    }

}