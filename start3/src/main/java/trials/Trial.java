package trials;

import java.util.function.Supplier;

public class Trial {

    static final int CLASS_CONSTANT = 50;
    private final String account;
    private int mark1;
    private int mark2;

    public Trial(String account, int mark1, int mark2) {
        this.account = account;
        this.mark1 = mark1;
        this.mark2 = mark2;
    }

    public Trial(Trial trial) {
        this(trial.getAccount(), trial.getMark1(), trial.getMark2());
    }

    public static int getClassConstant() {
        return CLASS_CONSTANT;
    }

    public String getAccount() {
        return account;
    }

    public int getMark1() {
        return mark1;
    }

    public int getMark2() {
        return mark2;
    }

    public Supplier<Integer> twoMarksSum = () -> this.getMark1() + this.getMark2();

    @Override
    public String toString() {
        return String.format("%s; trial is passed - %s"
                , fieldsToString(), isPassed());
    }

    protected String fieldsToString() {
        return String.format("%s; His marks : %s; %s"
                , getAccount(), getMark1(), getMark2());
    }

    public void clearMarks() {
        mark1 = 0;
        mark2 = 0;
    }

    public Trial copy() {
        return new Trial(this);
    }

    public boolean isPassed() {
        return Integer.sum(getMark1(), getMark2()) >= getClassConstant();
    }
}
