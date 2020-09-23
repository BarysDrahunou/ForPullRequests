package writers;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import trials.Trial;

import java.util.concurrent.BlockingQueue;

import static org.mockito.Mockito.*;

public class TrialWriterTest {

    @Mock
    BlockingQueue<Trial> blockingQueue;
    @Mock
    TrialConsumer consumer;
    @Mock
    Trial trial;
    TrialWriter trialWriter;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        trialWriter = new TrialWriter(blockingQueue, consumer, 1);
    }

    @Test
    public void go() throws InterruptedException {
        when(blockingQueue.take()).thenReturn(trial).thenReturn(trial)
                .thenReturn(new Trial("Final trial", 0, 0));
        trialWriter.go();
        verify(consumer, times(2)).writeTrial(trial);
    }
}