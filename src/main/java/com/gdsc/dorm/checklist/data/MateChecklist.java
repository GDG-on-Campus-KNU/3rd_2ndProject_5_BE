package com.gdsc.dorm.checklist.data;

import com.gdsc.dorm.checklist.data.option.AwakeTime;
import com.gdsc.dorm.checklist.data.option.CleaningFreq;
import com.gdsc.dorm.checklist.data.option.ShowerTakeTime;
import com.gdsc.dorm.checklist.data.option.ShowerTime;
import com.gdsc.dorm.checklist.data.option.SleepHabit;
import com.gdsc.dorm.checklist.data.option.SleepTime;
import com.gdsc.dorm.checklist.data.option.Smoke;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class MateChecklist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private SleepTime sleepTime;
    private AwakeTime awakeTime;
    private ShowerTakeTime showerTakeTime;
    private ShowerTime showerTime;
    private CleaningFreq cleaningFreq;
    private List<SleepHabit> sleepHabits;
    private Smoke smoke;

    @Builder
    public MateChecklist(SleepTime sleepTime, AwakeTime awakeTime, ShowerTakeTime showerTakeTime,
                         ShowerTime showerTime, CleaningFreq cleaningFreq, List<SleepHabit> sleepHabits,
                         Smoke smoke) {
        this.sleepTime = sleepTime;
        this.awakeTime = awakeTime;
        this.showerTakeTime = showerTakeTime;
        this.showerTime = showerTime;
        this.cleaningFreq = cleaningFreq;
        this.sleepHabits = sleepHabits;
        this.smoke = smoke;
    }
}
