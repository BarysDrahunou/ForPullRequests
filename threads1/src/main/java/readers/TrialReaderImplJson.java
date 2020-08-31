package readers;

import com.google.gson.*;
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
    private FileReader fileReader;
    private final JsonStreamParser parser;

    public TrialReaderImplJson(String reader) throws FileNotFoundException {
        parser = getParser(reader);
    }

    private JsonStreamParser getParser(String reader) throws FileNotFoundException {
        URL url = this.getClass().getClassLoader().getResource(reader);
        File file = new File(Objects.requireNonNull(url).getPath());
        fileReader = new FileReader(file);
        return new JsonStreamParser(fileReader);
    }

    @Override
    public boolean hasTrial() {
        try {
            return parser.hasNext();
        } catch (JsonSyntaxException | JsonIOException e) {
            return false;
        }
    }

    @Override
    public Optional<Trial> nextTrial() {
        try {
            JsonObject trial = new GsonBuilder().create().fromJson(parser.next(), JsonObject.class);
            String className = trial.get("class").getAsString();
            JsonObject args = trial.get("args").getAsJsonObject();
            String account = args.get("account").getAsString();
            int mark1 = args.get("mark1").getAsInt();
            int mark2 = args.get("mark2").getAsInt();
            JsonElement lastMark=args.get("mark3");
            int mark3 = Objects.nonNull(lastMark)?lastMark.getAsInt():0;
            return FactoryOfTrials.getTrial(className, account, mark1, mark2, mark3);
        } catch (NullPointerException | IllegalArgumentException | JsonSyntaxException
                | JsonIOException | UnsupportedOperationException e) {
            LOGGER.error(e);
            return Optional.empty();
        }
    }

    @Override
    public void close() throws IOException {
        fileReader.close();
    }

}
