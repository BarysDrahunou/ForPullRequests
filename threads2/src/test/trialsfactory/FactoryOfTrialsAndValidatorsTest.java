package trialsfactory;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;
import trials.ExtraTrial;
import trials.Trial;

import java.util.Optional;

import static org.junit.Assert.*;

public class FactoryOfTrialsAndValidatorsTest {

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getTrialTest() {

        Trial trial = FactoryOfTrials.getTrial("Trial", "Vadim"
                , 50, 80, 0).orElse(null);
        assertEquals(new Trial("Vadim", 50, 80),trial);
        Trial trial1=FactoryOfTrials.getTrial("ExtraTrial", "Vadim"
                , 50, 80, 0).orElse(null);
        assertEquals(new ExtraTrial("Vadim", 50, 80,0),trial1);
        Optional<Trial> trial2=FactoryOfTrials.getTrial("LightTrial", ""
                , 50, 80, 0);
        assertEquals(Optional.empty(),trial2);
        Optional<Trial> trial3=FactoryOfTrials.getTrial("StrongTrial", "Vadim"
                , -50, 80, 0);
        assertEquals(Optional.empty(),trial3);
        Optional<Trial> trial4=FactoryOfTrials.getTrial("SomeNewTrial", "Vadim"
                , 50, 800, 0);
        assertEquals(Optional.empty(),trial4);
    }

    @Test
    public void getTrialClassIllegalArgumentExceptionTest() {
        Optional<Trial> trial=FactoryOfTrials.getTrial("SomeNewTrial", "Vadim"
                , 50, 800, 0);
        assertEquals(Optional.empty(),trial);
    }
}