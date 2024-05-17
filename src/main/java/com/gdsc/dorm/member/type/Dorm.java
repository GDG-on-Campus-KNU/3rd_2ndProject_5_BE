package com.gdsc.dorm.member.type;

public enum Dorm {
    BONGSA("봉사관"),
    TRUTH("진리관"),
    HWAMOK("화목관"),
    HYANG("향토관"),
    CHEOM("첨성관"),
    NURI("누리관"),
    BORAM("보람관"),
    JAJU("자주관"),
    CHANG("창조관"),
    GYEONG("경애관"),
    GEUN("근면관"),
    NOAK("노악관"),
    CHEONG("청운관");

    private String title;

    Dorm(String title) { this.title = title; }

    public String getTitle() {
        return title;
    }
}
