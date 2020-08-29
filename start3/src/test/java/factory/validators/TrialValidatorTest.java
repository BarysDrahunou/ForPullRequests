package factory.validators;

import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import trials.*;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;


public class TrialValidatorTest {

    TrialValidator trialValidator;
    TrialValidator lightTrialValidator;
    TrialValidator strongTrialValidator;
    @Mock
    JsonObject jsonObject;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        trialValidator = new TrialValidator(Trial.class);
        lightTrialValidator = new TrialValidator(LightTrial.class);
        strongTrialValidator = new TrialValidator(StrongTrial.class);
    }

    @Test
    public void getValidTrialTest(){
        when(jsonObject.size()).thenReturn(3);
        when(jsonObject.get("account")).thenReturn(jsonObject);
        when(jsonObject.getAsString()).thenReturn("Vasya");
        when(jsonObject.get("mark1")).thenReturn(jsonObject);
        when(jsonObject.get("mark2")).thenReturn(jsonObject);
        when(jsonObject.getAsInt()).thenReturn(30).thenReturn(50)
                .thenReturn(30).thenReturn(50).thenReturn(30).thenReturn(50);
        Trial trial = trialValidator.getValidTrial(jsonObject);
        Trial lightTrial = lightTrialValidator.getValidTrial(jsonObject);
        Trial strongTrial = strongTrialValidator.getValidTrial(jsonObject);
        verify(jsonObject, times(9)).remove(anyString());
        assertEquals(new Trial("Vasya", 30, 50).toString(), trial.toString());
        assertEquals(new LightTrial("Vasya", 30, 50).toString(), lightTrial.toString());
        assertEquals(new StrongTrial("Vasya", 30, 50).toString(), strongTrial.toString());
    }

    @Test(expected = JsonSyntaxException.class)
    public void getValidTrialNotEnoughArgumentsTest(){
        when(jsonObject.size()).thenReturn(2);
        try {
            trialValidator.getValidTrial(jsonObject);
        } catch (JsonSyntaxException e) {
            try {
                lightTrialValidator.getValidTrial(jsonObject);
            } catch (JsonSyntaxException e1) {
                strongTrialValidator.getValidTrial(jsonObject);
            }
        }
    }

    @Test(expected = JsonSyntaxException.class)
    public void getValidTrialStringFieldInvalidTest(){
        when(jsonObject.size()).thenReturn(3);
        when(jsonObject.get("account")).thenReturn(jsonObject);
        when(jsonObject.getAsString()).thenReturn(null);
        when(jsonObject.get("mark1")).thenReturn(jsonObject);
        when(jsonObject.get("mark2")).thenReturn(jsonObject);
        when(jsonObject.getAsInt()).thenReturn(30);
        try {
            trialValidator.getValidTrial(jsonObject);
        } catch (JsonSyntaxException e) {
            try {
                lightTrialValidator.getValidTrial(jsonObject);
            } catch (JsonSyntaxException e1) {
                strongTrialValidator.getValidTrial(jsonObject);
            }
        }
    }

    @Test(expected = JsonSyntaxException.class)
    public void getValidTrialIntegerFieldInvalidTest(){
        when(jsonObject.size()).thenReturn(3);
        when(jsonObject.get("account")).thenReturn(jsonObject);
        when(jsonObject.getAsString()).thenReturn("Vasya");
        when(jsonObject.get("mark1")).thenReturn(jsonObject);
        when(jsonObject.get("mark2")).thenReturn(jsonObject);
        when(jsonObject.getAsInt()).thenReturn(30).thenReturn(100500);
        try {
            trialValidator.getValidTrial(jsonObject);
        } catch (JsonSyntaxException e) {
            try {
                lightTrialValidator.getValidTrial(jsonObject);
            } catch (JsonSyntaxException e1) {
                strongTrialValidator.getValidTrial(jsonObject);
            }
        }
    }
}
