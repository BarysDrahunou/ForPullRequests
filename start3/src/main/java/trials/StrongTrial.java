package trials;

public class StrongTrial extends Trial {

    public StrongTrial(){}

    public StrongTrial(String account, int mark1, int mark2) {
        super(account, mark1, mark2);
    }

    public StrongTrial(StrongTrial trial) {
        this(trial.getAccount(), trial.getMark1(), trial.getMark2());
    }

    @Override
    public Trial copy() {
        return new StrongTrial(super.getAccount(), super.getMark1(), super.getMark2());
    }

    @Override
    public boolean isPassed() {
        return getMark1() / 2 + getMark2() >= CLASS_CONSTANT;
    }
}
