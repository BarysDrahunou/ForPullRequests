package trials;

public class LightTrial extends Trial {
    static final int CLASS_CONSTANT_FOR_TEST1 = 10;
    static final int CLASS_CONSTANT_FOR_TEST2 = 20;

    public LightTrial(String account, int mark1, int mark2) {
        super(account, mark1, mark2);
    }

    public LightTrial(LightTrial trial) {
        this(trial.getAccount(), trial.getMark1(), trial.getMark2());
    }

    public static int getClassConstantForTest1() {
        return CLASS_CONSTANT_FOR_TEST1;
    }

    public static int getClassConstantForTest2() {
        return CLASS_CONSTANT_FOR_TEST2;
    }

    @Override
    public Trial copy() {
        return new LightTrial(super.getAccount(), super.getMark1(), super.getMark2());
    }

    @Override
    public boolean isPassed() {
        return getMark1() >= getClassConstantForTest1() && getMark2() >= getClassConstantForTest2();
    }
}
