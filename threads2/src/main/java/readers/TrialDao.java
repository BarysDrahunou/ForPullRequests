package readers;

import trials.Trial;

import java.util.Optional;

public interface TrialDao extends AutoCloseable {

    Optional<Trial> nextTrial();

    boolean hasTrial();

}
