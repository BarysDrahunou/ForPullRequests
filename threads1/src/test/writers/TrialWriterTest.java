package writers;

import buffer.TrialBuffer;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import trials.Trial;

import static org.mockito.Mockito.*;

public class TrialWriterTest {

    @Mock
    TrialBuffer trialBuffer;
    @Mock
    TrialConsumer consumer;
    @Mock
    Trial trial;
    @InjectMocks
    TrialWriter trialWriter;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void go() {
        when(trialBuffer.takeTrial()).thenReturn(trial).thenReturn(trial)
                .thenReturn(new Trial("Final trial",0,0));
        trialWriter.go();
        verify(consumer, times(2)).writeTrial(trial);
    }
}