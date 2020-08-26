package trials;

public class ExtraTrial extends Trial {

    static final int CLASS_CONSTANT_FOR_EXTRA_TRIAL = 40;
    private int mark3;

    public ExtraTrial() {
    }

    public ExtraTrial(String account, int mark1, int mark2, int mark3) {
        super(account, mark1, mark2);
        this.mark3 = mark3;
    }

    public ExtraTrial(ExtraTrial trial) {
        this(trial.getAccount(), trial.getMark1(), trial.getMark2(), trial.getMark3());
    }

    public int getMark3() {
        return mark3;
    }

    public void setMark3(int mark3) {
        this.mark3 = mark3;
    }

    @Override
    public String toString() {
        return String.format("%s; %s; trial is passed - %s"
                , super.fieldsToString(), getMark3(), isPassed());
    }

    @Override
    public void clearMarks() {
        super.clearMarks();
        mark3 = 0;
    }

    @Override
    public Trial copy() {
        return new ExtraTrial(this);
    }

    @Override
    public boolean isPassed() {
        return super.isPassed() && getMark3() >= CLASS_CONSTANT_FOR_EXTRA_TRIAL;
    }
}
