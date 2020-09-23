package writers;

import trials.*;

import java.io.IOException;
import java.sql.SQLException;

public interface TrialConsumer extends AutoCloseable {

    void writeTrial(Trial trial);

    void setWriter(String writer, String configurationFileName);

    @Override
    void close() throws IOException, SQLException;
}
