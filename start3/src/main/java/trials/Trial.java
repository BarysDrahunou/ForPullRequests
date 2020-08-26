package trials;

public class Trial {

    public static final int CLASS_CONSTANT = 50;
    private String account;
    private int mark1;
    private int mark2;

    public Trial() {
    }

    public Trial(String account, int mark1, int mark2) {
        this.account = account;
        this.mark1 = mark1;
        this.mark2 = mark2;
    }

    public Trial(Trial trial) {
        this(trial.getAccount(), trial.getMark1(), trial.getMark2());
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

    public void setAccount(String account) {
        this.account = account;
    }

    public void setMark1(int mark1) {
        this.mark1 = mark1;
    }

    public void setMark2(int mark2) {
        this.mark2 = mark2;
    }

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
        return mark1 + mark2 >= CLASS_CONSTANT;
    }
}
