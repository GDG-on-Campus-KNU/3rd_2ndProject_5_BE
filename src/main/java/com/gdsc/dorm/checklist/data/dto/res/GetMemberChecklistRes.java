package com.gdsc.dorm.checklist.data.dto.res;

import com.gdsc.dorm.checklist.data.UserChecklist;
import com.gdsc.dorm.checklist.data.option.AwakeTime;
import com.gdsc.dorm.checklist.data.option.CleaningFreq;
import com.gdsc.dorm.checklist.data.option.ItemShare;
import com.gdsc.dorm.checklist.data.option.ShowerTakeTime;
import com.gdsc.dorm.checklist.data.option.ShowerTime;
import com.gdsc.dorm.checklist.data.option.SleepHabit;
import com.gdsc.dorm.checklist.data.option.SleepTime;
import com.gdsc.dorm.checklist.data.option.Smoke;
import java.util.List;
import lombok.Data;

@Data
public class GetMemberChecklistRes {
    private SleepTime sleepTime;
    private AwakeTime awakeTime;
    private ShowerTakeTime showerTakeTime;
    private ShowerTime showerTime;
    private CleaningFreq cleaningFreq;
    private List<SleepHabit> sleepHabits;
    private int hot;
    private int cold;
    private Smoke smoke;
    private ItemShare itemShare;

    public GetMemberChecklistRes(UserChecklist userChecklist) {
        this.sleepTime = userChecklist.getSleepTime();
        this.awakeTime = userChecklist.getAwakeTime();
        this.showerTakeTime = userChecklist.getShowerTakeTime();
        this.showerTime = userChecklist.getShowerTime();
        this.cleaningFreq = userChecklist.getCleaningFreq();
        this.sleepHabits = userChecklist.getSleepHabits();
        this.hot = userChecklist.getHot();
        this.cold = userChecklist.getCold();
        this.smoke = userChecklist.getSmoke();
        this.itemShare = userChecklist.getItemShare();
    }
}
