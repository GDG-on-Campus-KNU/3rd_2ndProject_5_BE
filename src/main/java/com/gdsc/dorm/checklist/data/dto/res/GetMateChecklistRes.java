package com.gdsc.dorm.checklist.data.dto.res;

import com.gdsc.dorm.checklist.data.MateChecklist;
import com.gdsc.dorm.checklist.data.option.AwakeTime;
import com.gdsc.dorm.checklist.data.option.CleaningFreq;
import com.gdsc.dorm.checklist.data.option.ShowerTakeTime;
import com.gdsc.dorm.checklist.data.option.ShowerTime;
import com.gdsc.dorm.checklist.data.option.SleepHabit;
import com.gdsc.dorm.checklist.data.option.SleepTime;
import com.gdsc.dorm.checklist.data.option.Smoke;
import java.util.List;
import lombok.Data;

@Data
public class GetMateChecklistRes {
    private SleepTime sleepTime;
    private AwakeTime awakeTime;
    private ShowerTakeTime showerTakeTime;
    private ShowerTime showerTime;
    private CleaningFreq cleaningFreq;
    private List<SleepHabit> sleepHabits;
    private Smoke smoke;

    public GetMateChecklistRes(MateChecklist mateChecklist) {
        this.sleepTime = mateChecklist.getSleepTime();
        this.awakeTime = mateChecklist.getAwakeTime();
        this.showerTakeTime = mateChecklist.getShowerTakeTime();
        this.showerTime = mateChecklist.getShowerTime();
        this.cleaningFreq = mateChecklist.getCleaningFreq();
        this.sleepHabits = mateChecklist.getSleepHabits();
        this.smoke = mateChecklist.getSmoke();
    }
}
