package com.gdsc.dorm.checklist.data.dto.req;

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
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class MakeMemberChecklistReq {
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

    public UserChecklist toEntity() {
        return UserChecklist.builder()
                .sleepTime(sleepTime)
                .awakeTime(awakeTime)
                .showerTakeTime(showerTakeTime)
                .showerTime(showerTime)
                .cleaningFreq(cleaningFreq)
                .sleepHabits(sleepHabits)
                .hot(hot)
                .cold(cold)
                .smoke(smoke)
                .itemShare(itemShare)
                .build();
    }
}
