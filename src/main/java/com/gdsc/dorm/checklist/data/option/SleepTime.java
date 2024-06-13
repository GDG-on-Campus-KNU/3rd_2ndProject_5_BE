package com.gdsc.dorm.checklist.data.option;

public enum SleepTime {
    PM10(1),
    PM11(2),
    MIDNIGHT(3),
    AM1(4),
    AM2(2);

    private int value;
    SleepTime(int value) { this.value = value; }
    public int getValue() {
        return value;
    }
}
