package trials;

public class ExtraTrial extends Trial {

    static final int CLASS_CONSTANT_FOR_EXTRA_TRIAL = 40;
    private int mark3;

    public ExtraTrial(){}

    public ExtraTrial(String account, int mark1, int mark2, int mark3) {
        super(account, mark1, mark2);
        this.mark3 = mark3;
    }

    public ExtraTrial(ExtraTrial trial) {
        this(trial.getAccount(), trial.getMark1(), trial.getMark2(), trial.getMark3());
    }

    public void setMark3(int mark3) {
        this.mark3 = mark3;
    }

    public int getMark3() {
        return mark3;
    }

    @Override
    public String toString() {
        return String.format("%s; %s; trial is passed - %s"
                , super.fieldsToString(), getMark3(), isPassed());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ExtraTrial)) return false;
        if (!super.equals(o)) return false;

        ExtraTrial that = (ExtraTrial) o;

        return mark3 == that.mark3;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + mark3;
        return result;
    }

    @Override
    public Trial getCopy() {
        return new ExtraTrial(this);
    }

    @Override
    public boolean isPassed() {
        return super.isPassed() && getMark3() >= CLASS_CONSTANT_FOR_EXTRA_TRIAL;
    }
}
