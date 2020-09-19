package readers;

import myexceptions.WrongArgumentException;
import trialsfactory.FactoryOfTrials;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import trials.Trial;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.Objects;
import java.util.Optional;
import java.util.Scanner;

public class TrialReaderImplCSV implements TrialDao {

    private static final Logger LOGGER = LogManager.getLogger();
    private final Scanner scanner;

    public TrialReaderImplCSV(String reader) {
        scanner = getScanner(reader);
    }

    private Scanner getScanner(String reader) {
        try {
            URL url = this.getClass().getClassLoader().getResource(reader);
            File file = new File(Objects.requireNonNull(url).getPath());
            return new Scanner(new FileInputStream(file));
        } catch (FileNotFoundException e) {
            throw new WrongArgumentException("File not found", reader, e);
        }
    }

    @Override
    public boolean hasTrial() {
        return scanner.hasNext();
    }

    @Override
    public Optional<Trial> nextTrial() {
        try {
            String csv = scanner.next();
            String[] csvSplit = csv.split(";");
            String className = csvSplit[0];
            String account = csvSplit[1];
            int mark1 = Integer.parseInt(csvSplit[2]);
            int mark2 = Integer.parseInt(csvSplit[3]);
            int mark3 = 0;
            if (csvSplit.length > 4) {
                mark3 = Integer.parseInt(csvSplit[4]);
            }
            return FactoryOfTrials.getTrial(className, account, mark1, mark2, mark3);
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            LOGGER.error(e);
            return Optional.empty();
        }
    }

    @Override
    public void close() {
        scanner.close();
    }

}
