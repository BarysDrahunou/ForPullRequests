package writers;

import trials.Trial;

public interface TrialConsumer extends AutoCloseable {

    void writeTrial(Trial trial);

}
