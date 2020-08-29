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
import java.util.function.ToIntFunction;
import java.util.stream.Collectors;

public class Runner {
    private static final Logger LOGGER = LogManager.getLogger();

    public static void main(String[] args) {
        try (FileReader fileReader = new FileReader(args[0])) {
            Type type = new TypeToken<List<JsonObject>>() {
            }.getType();
            List<JsonObject> jsonObjects = new Gson().fromJson(fileReader, type);
            List<Trial> trials = getAllTrialsFromJson(jsonObjects);        //1
            trials.forEach(LOGGER::info);        //2
            printTheNumberOfPassedTrials(trials);       //3
            ToIntFunction<Trial> twoMarksSum = trial -> trial.getMark1() + trial.getMark2();
            trials.sort(Comparator.comparing(twoMarksSum::applyAsInt));           //4
            printSum(trials,twoMarksSum);         //5
            printFailedTrials(trials);      //6
            printNumericArray(trials,twoMarksSum);     //7
        } catch (IOException e) {
            LOGGER.error(e);
        }
    }

    private static List<Trial> getAllTrialsFromJson(List<JsonObject> jsonObjects) {
        return jsonObjects.stream()
                .map(FactoryOfTrials::getTrial)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }

    private static void printTheNumberOfPassedTrials(List<Trial> trials) {
        LOGGER.info(String.format("Number of successful passed tests: %s"
                , trials.stream().filter(Trial::isPassed).count()));
        LOGGER.info("_________________________");
    }

    private static void printSum(List<Trial> trials, ToIntFunction<Trial> twoMarksSum) {
        LOGGER.info("Sum of the first and the second marks from the collection:");
        trials.stream().map(twoMarksSum::applyAsInt).forEach(LOGGER::info);
        LOGGER.info("_________________________");
    }

    private static void printFailedTrials(List<Trial> trials) {
        List<Trial> list = trials.stream().filter(x -> !x.isPassed()).map(Trial::copy)
                .peek(Trial::clearMarks).peek(LOGGER::info).collect(Collectors.toList());
        LOGGER.info(list.stream().noneMatch(Trial::isPassed));
        LOGGER.info("_________________________");
    }

    private static void printNumericArray(List<Trial> trials, ToIntFunction<Trial> twoMarksSum) {
        int[] array = trials.stream().mapToInt(twoMarksSum).toArray();
        String stringList = Arrays.stream(array).boxed()
                .map(String::valueOf).collect(Collectors.joining(", "));
        LOGGER.info(stringList);
        LOGGER.info("_________________________");
    }
}
