package trials;

import java.util.Objects;

public class Trial {

    static final int CLASS_CONSTANT = 50;
    private String account;
    private int mark1;
    private int mark2;

    public Trial(){}

    public Trial(String account, int mark1, int mark2) {
        this.account = account;
        this.mark1 = mark1;
        this.mark2 = mark2;
    }

    public Trial(Trial trial) {
        this(trial.getAccount(), trial.getMark1(), trial.getMark2());
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public void setMark1(int mark1) {
        this.mark1 = mark1;
    }

    public void setMark2(int mark2) {
        this.mark2 = mark2;
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

    @Override
    public String toString() {
        return String.format("%s; trial is passed - %s"
                , fieldsToString(), isPassed());
    }

    public String fieldsToString() {
        return String.format("%s; His marks : %s; %s"
                , getAccount(), getMark1(), getMark2());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Trial)) return false;

        Trial trial = (Trial) o;

        if (mark1 != trial.mark1) return false;
        if (mark2 != trial.mark2) return false;
        return Objects.equals(account, trial.account);
    }

    @Override
    public int hashCode() {
        return Objects.hash(account, mark1, mark2);
    }

    public Trial getCopy() {
        return new Trial(this);
    }

    public boolean isPassed() {
        return getMark1() + getMark2() >= CLASS_CONSTANT;
    }
}
