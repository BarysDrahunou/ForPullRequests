package readers;

import buffer.TrialBuffer;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import trials.Trial;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class TrialReaderTest {
    @Mock
    TrialBuffer trialBuffer;
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
    public void runTest(){
        when(trialDao.hasTrial()).thenReturn(true).thenReturn(true).thenReturn(false);
        when(trialDao.nextTrial()).thenReturn(Optional.of(trial));
        trialReader.run();
        verify(trialBuffer,times(3)).putTrial(any());
    }
}