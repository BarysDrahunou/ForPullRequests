package factory;

import com.google.gson.JsonObject;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class FactoryOfTrialsTest {

    @Mock
    JsonObject jsonObject;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getTrialTest() {
        when(jsonObject.size()).thenReturn(1);
        when(jsonObject.get("class")).thenReturn(jsonObject);
        when(jsonObject.getAsString()).thenReturn("Trial");
        when(jsonObject.get("args")).thenReturn(jsonObject);
        when(jsonObject.getAsJsonObject()).thenReturn(jsonObject);
        FactoryOfTrials.getTrial(jsonObject);
        verify(jsonObject,times(2)).get(anyString());
        verify(jsonObject,times(2)).remove(anyString());
        verify(jsonObject).size();
    }
    @Test
    public void getTrialClassNotFoundExceptionTest() {
        when(jsonObject.get("class")).thenReturn(jsonObject);
        when(jsonObject.getAsString()).thenThrow(UnsupportedOperationException.class);
        when(jsonObject.get("args")).thenReturn(jsonObject);
       when(jsonObject.getAsJsonObject()).thenReturn(jsonObject);
      assertEquals(Optional.empty(),FactoryOfTrials.getTrial(jsonObject));
    }
}