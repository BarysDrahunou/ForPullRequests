package readers;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import trials.Trial;

import java.util.Optional;
import java.util.concurrent.BlockingQueue;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class TrialReaderTest {

    @Mock
    BlockingQueue<Trial> blockingQueue;
    @Mock
    TrialDao trialDao;
    @Mock
    Trial trial;
    @InjectMocks
    TrialReader trialReader;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void runTest() throws InterruptedException {
        when(trialDao.hasTrial()).thenReturn(true).thenReturn(true).thenReturn(false);
        when(trialDao.nextTrial()).thenReturn(Optional.of(trial));
        trialReader.run();
        verify(blockingQueue, times(3)).put(any());
    }
}