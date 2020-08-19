import factory.FactoryOfTrials;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import trials.*;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.*;
import java.util.stream.Collectors;

public class Runner {
    private static final Logger LOGGER = LogManager.getLogger();
    private static List<Trial> trials;

    public static void main(String[] args) {
        try {
            Type type = new TypeToken<List<JsonObject>>() {
            }.getType();
            List<JsonObject> jsonObjects = new Gson().fromJson(new FileReader(args[0]), type);
            trials = getAllTrialsFromJson(jsonObjects);        //1
            trials.forEach(LOGGER::info);        //2
            printTheNumberOfPassedTrials();       //3
            trials.sort(Comparator.comparingInt(trial -> trial.twoMarksSum.get()));           //4
            printSum();         //5
            printFailedTrials();      //6
            printNumericArray(trials);     //7
        } catch (IOException e) {
            LOGGER.error(e);
            e.printStackTrace();
        }
    }

    private static List<Trial> getAllTrialsFromJson(List<JsonObject> jsonObjects) {
        return jsonObjects.stream()
                .map(FactoryOfTrials::getTrial)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }

    private static void printTheNumberOfPassedTrials() {
        LOGGER.info(String.format("Number of successful passed tests: %s"
                , trials.stream().filter(Trial::isPassed).count()));
        LOGGER.info("_________________________");
    }

    private static void printSum() {
        LOGGER.info("Sum of first and second marks from the collection:");
        trials.forEach(trial -> LOGGER.info(trial.twoMarksSum.get()));
        LOGGER.info("_________________________");
    }

    private static void printFailedTrials() {
        List<Trial> list = trials.stream().filter(x -> !x.isPassed()).map(Trial::copy)
                .peek(Trial::clearMarks).peek(LOGGER::info).collect(Collectors.toList());
        LOGGER.info(list.stream().noneMatch(Trial::isPassed));
        LOGGER.info("_________________________");
    }

    private static void printNumericArray(List<Trial> trial) {
        int[] array = trial.stream().mapToInt(x -> x.twoMarksSum.get()).toArray();
        String stringList = Arrays.stream(array).boxed()
                .map(String::valueOf).collect(Collectors.joining(", "));
        LOGGER.info(stringList);
        LOGGER.info("_________________________");
    }
}
