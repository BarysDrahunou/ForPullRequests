package readers;

import trials.Trial;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

public interface TrialDao extends AutoCloseable {

    Optional<Trial> nextTrial();

    boolean hasTrial();

    void setReader(String reader, String configurationFileName);

    TrialDao getCopy();

    @Override
    void close() throws IOException, SQLException;
}
