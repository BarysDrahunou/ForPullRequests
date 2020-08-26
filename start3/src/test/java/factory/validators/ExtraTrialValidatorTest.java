package factory.validators;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import trials.*;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class ExtraTrialValidatorTest {
    ExtraTrialValidator extraTrialValidator;
    @Mock
    JsonElement jsonElement;
    @Mock
    JsonObject jsonObject;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        extraTrialValidator = new ExtraTrialValidator(ExtraTrial.class);

    }

    @Test
    public void getValidTrialTest() {
        when(jsonElement.getAsJsonObject()).thenReturn(jsonObject);
        when(jsonObject.size()).thenReturn(4);
        when(jsonObject.get("account")).thenReturn(jsonObject);
        when(jsonObject.getAsString()).thenReturn("Vasya");
        when(jsonObject.get("mark1")).thenReturn(jsonObject);
        when(jsonObject.get("mark2")).thenReturn(jsonObject);
        when(jsonObject.get("mark3")).thenReturn(jsonObject);
        when(jsonObject.getAsInt()).thenReturn(30).thenReturn(50).thenReturn(80);
        ExtraTrial trial = extraTrialValidator.getValidTrial(jsonElement);
        verify(jsonObject,times(4)).remove(anyString());
        assertEquals(new ExtraTrial("Vasya", 30, 50, 80)
                .toString(), trial.toString());
    }

    @Test(expected = JsonSyntaxException.class)
    public void getValidTrialNotEnoughArgumentsTest() {
        when(jsonElement.getAsJsonObject()).thenReturn(jsonObject);
        when(jsonObject.size()).thenReturn(3);
        extraTrialValidator.getValidTrial(jsonElement);

    }
}