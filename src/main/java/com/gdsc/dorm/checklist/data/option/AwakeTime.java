package com.gdsc.dorm.checklist.data.option;

public enum AwakeTime {
    BEFORE6(1),
    AM6(2),
    AM7(3),
    AM(4),
    AFTER9(5);

    private int value;
    AwakeTime(int value) {this.value = value;}

    public int getValue() {
        return value;
    }
}
