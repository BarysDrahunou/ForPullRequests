package readers;

import com.google.gson.stream.JsonReader;
import myexceptions.WrongArgumentException;
import trialsfactory.TrialsFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import trials.Trial;

import java.io.*;
import java.net.URL;
import java.util.Objects;
import java.util.Optional;

import static constants.ExceptionsMessages.*;
import static constants.TrialsConstants.*;

public class JsonTrialReader implements TrialDao {

    private static final Logger LOGGER = LogManager.getLogger();
    private JsonReader reader;

    public JsonTrialReader() {
    }

    @Override
    public boolean hasTrial() {
        try {
            return reader.hasNext();
        } catch (IOException e) {
            LOGGER.error(e);
            return false;
        }
    }

    @Override
    public Optional<Trial> nextTrial() {
        try {
            String className = "";
            String account = "";
            int mark1 = -1;
            int mark2 = -1;
            int mark3 = -1;
            reader.beginObject();
            while (reader.hasNext()) {
                switch (reader.nextName()) {
                    case TRIAL_CLASS -> {
                        try {
                            className = reader.nextString();
                        } catch (IllegalStateException e) {
                            LOGGER.error(e);
                            reader.skipValue();
                        }
                    }
                    case TRIAL_ARGS -> {
                        reader.beginObject();
                        while (reader.hasNext()) {
                            try {
                                switch (reader.nextName()) {
                                    case TRIAL_ACCOUNT -> account = reader.nextString();
                                    case TRIAL_MARK1 -> mark1 = reader.nextInt();
                                    case TRIAL_MARK2 -> mark2 = reader.nextInt();
                                    case TRIAL_MARK3 -> mark3 = reader.nextInt();
                                    default -> reader.skipValue();
                                }
                            } catch (IllegalStateException e) {
                                LOGGER.error(e);
                                reader.skipValue();
                            }
                        }
                        reader.endObject();
                    }
                    default -> reader.skipValue();
                }
            }
            reader.endObject();
            return TrialsFactory.getTrial(className, account, mark1, mark2, mark3);
        } catch (IOException e) {
            LOGGER.error(e);
            return Optional.empty();
        }
    }

    @Override
    public void setReader(String reader, String configurationFileName) {
        this.reader = getJsonReader(reader);
        try {
            this.reader.beginArray();
        } catch (IOException e) {
            throw new WrongArgumentException(BEGIN_ARRAY_PROBLEM, reader, e);
        }
    }

    private JsonReader getJsonReader(String reader) {
        try {
            URL url = this.getClass().getClassLoader().getResource(reader);
            File file = new File(Objects.requireNonNull(url).getPath());
            return new JsonReader(new FileReader(file));
        } catch (FileNotFoundException | NullPointerException e) {
            throw new WrongArgumentException(FILE_DOES_NOT_EXIST, reader, e);
        }
    }

    @Override
    public void close() throws IOException {
        reader.endArray();
        reader.close();
    }
}
