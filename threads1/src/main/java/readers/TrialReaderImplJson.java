package readers;

import com.google.gson.stream.JsonReader;
import trialsfactory.FactoryOfTrials;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import trials.Trial;

import java.io.*;
import java.net.URL;
import java.util.Objects;
import java.util.Optional;

public class TrialReaderImplJson implements TrialDao {

    private static final Logger LOGGER = LogManager.getLogger();
    private final JsonReader reader;

    public TrialReaderImplJson(String reader) throws IOException {
        this.reader = getJsonReader(reader);
        this.reader.beginArray();
    }

    private JsonReader getJsonReader(String reader) throws FileNotFoundException {
        URL url = this.getClass().getClassLoader().getResource(reader);
        File file = new File(Objects.requireNonNull(url).getPath());
        return new JsonReader(new FileReader(file));
    }

    @Override
    public boolean hasTrial() {
        try {
            return reader.hasNext();
        } catch (IOException e) {
            return false;
        }
    }

    @Override
    public Optional<Trial> nextTrial() {
        try {
            String className="";
            String account="";
            int mark1=-1;
            int mark2=-1;
            int mark3=-1;
            reader.beginObject();
            while (reader.hasNext()) {
                switch (reader.nextName()) {
                    case "class" -> {
                        try {
                            className = reader.nextString();
                        } catch (IllegalStateException e) {
                            reader.skipValue();
                        }
                    }
                    case "args" -> {
                        reader.beginObject();
                        while (reader.hasNext()) {
                            try{
                                switch (reader.nextName()) {
                                    case "account" -> account = reader.nextString();
                                    case "mark1" -> mark1 = reader.nextInt();
                                    case "mark2" -> mark2 = reader.nextInt();
                                    case "mark3" -> mark3 = reader.nextInt();
                                    default -> reader.skipValue();
                                }
                            }
                            catch (IllegalStateException e){
                                reader.skipValue();
                            }
                        }
                        reader.endObject();
                    }
                    default -> reader.skipValue();
                }
            }
            reader.endObject();
            return FactoryOfTrials.getTrial(className, account, mark1, mark2, mark3);
        } catch (IOException e) {
            LOGGER.error(e);
            return Optional.empty();
        }
    }

    @Override
    public void close() throws IOException {
        reader.endArray();
        reader.close();
    }

}
