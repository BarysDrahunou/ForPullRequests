package readers;

import myexceptions.WrongArgumentException;
import trialsfactory.TrialsFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import trials.Trial;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.*;

import static constants.ExceptionsMessages.*;
import static constants.ReaderWriterConstants.CSV_SEPARATOR;

public class CsvTrialReader implements TrialDao {

    private static final int CLASS_NAME_PARAM = 0;
    private static final int ACCOUNT_PARAM = 1;
    private static final int MARK1_PARAM = 2;
    private static final int MARK2_PARAM = 3;
    private static final int MARK3_PARAM = 4;
    private static final int ARGUMENTS_NUMBER = 4;
    private static final Logger LOGGER = LogManager.getLogger();
    private Scanner scanner;

    public CsvTrialReader() {
    }

    @Override
    public boolean hasTrial() {
        return scanner.hasNext();
    }

    @Override
    public Optional<Trial> nextTrial() {
        try {
            String csv = scanner.next();
            String[] csvSplit = csv.split(CSV_SEPARATOR);
            String className = csvSplit[CLASS_NAME_PARAM];
            String account = csvSplit[ACCOUNT_PARAM];
            int mark1 = Integer.parseInt(csvSplit[MARK1_PARAM]);
            int mark2 = Integer.parseInt(csvSplit[MARK2_PARAM]);
            int mark3 = 0;
            if (csvSplit.length > ARGUMENTS_NUMBER) {
                mark3 = Integer.parseInt(csvSplit[MARK3_PARAM]);
            }
            return TrialsFactory.getTrial(className, account, mark1, mark2, mark3);
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            LOGGER.error(e);
            return Optional.empty();
        }
    }

    @Override
    public void setReader(String reader, String configurationFileName) {
        scanner = getScanner(reader);
    }

    private Scanner getScanner(String reader) {
        try {
            URL url = this.getClass().getClassLoader().getResource(reader);
            File file = new File(Objects.requireNonNull(url).getPath());
            return new Scanner(new FileInputStream(file));
        } catch (FileNotFoundException | NullPointerException e) {
            throw new WrongArgumentException(FILE_DOES_NOT_EXIST, reader, e);
        }
    }

    @Override
    public void close() {
        scanner.close();
    }
}
