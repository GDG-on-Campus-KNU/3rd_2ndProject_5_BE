package com.gdsc.dorm.checklist.data.option;

public enum ShowerTakeTime {
    MIN20(1),
    MIN30(2),
    MIN40(3),
    MIN50(4),
    MIN60(5);

    private int value;
    ShowerTakeTime(int value) { this.value = value; }

    public int getValue() {
        return value;
    }
}
