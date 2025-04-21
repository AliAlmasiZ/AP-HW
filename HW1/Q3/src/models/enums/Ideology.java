package models.enums;

public enum Ideology {
    DEMOCRACY(50),
    COMMUNISM(60),
    FASCISM(30);

    private final int lockPercent;

    Ideology(int lockPercent) {
        this.lockPercent = lockPercent;
    }

    public int getLockPercent() {
        return lockPercent;
    }
}
