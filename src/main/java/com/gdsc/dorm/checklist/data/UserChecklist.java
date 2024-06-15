package com.gdsc.dorm.checklist.data;

import com.gdsc.dorm.checklist.data.option.AwakeTime;
import com.gdsc.dorm.checklist.data.option.CleaningFreq;
import com.gdsc.dorm.checklist.data.option.ItemShare;
import com.gdsc.dorm.checklist.data.option.ShowerTakeTime;
import com.gdsc.dorm.checklist.data.option.ShowerTime;
import com.gdsc.dorm.checklist.data.option.SleepHabit;
import com.gdsc.dorm.checklist.data.option.SleepTime;
import com.gdsc.dorm.checklist.data.option.Smoke;
import com.gdsc.dorm.member.data.Member;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@NoArgsConstructor
@Getter
@DynamicUpdate
public class UserChecklist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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

    @Builder
    public UserChecklist(SleepTime sleepTime, AwakeTime awakeTime, ShowerTakeTime showerTakeTime,
                         ShowerTime showerTime, CleaningFreq cleaningFreq, List<SleepHabit> sleepHabits,
                         int hot, int cold, Smoke smoke, ItemShare itemShare) {
        this.sleepTime = sleepTime;
        this.awakeTime = awakeTime;
        this.showerTakeTime = showerTakeTime;
        this.showerTime = showerTime;
        this.cleaningFreq = cleaningFreq;
        this.sleepHabits = sleepHabits;
        this.hot = hot;
        this.cold = cold;
        this.smoke = smoke;
        this.itemShare = itemShare;
    }

    public void update(UserChecklist newChecklist) {
        if (newChecklist.getSleepTime() != null)
            this.sleepTime = newChecklist.getSleepTime();
        if (newChecklist.getAwakeTime() != null)
            this.awakeTime = newChecklist.getAwakeTime();
        if (newChecklist.getShowerTakeTime() != null)
            this.showerTakeTime = newChecklist.getShowerTakeTime();
        if (newChecklist.getShowerTime() != null)
            this.showerTime = newChecklist.getShowerTime();
        if (newChecklist.getCleaningFreq() != null)
            this.cleaningFreq = newChecklist.getCleaningFreq();
        if (newChecklist.getSleepHabits() != null)
            this.sleepHabits = newChecklist.getSleepHabits();
        if (newChecklist.getHot() != 0)
            this.hot = newChecklist.getHot();
        if (newChecklist.getCold() != 0)
            this.cold = newChecklist.getCold();
        if (newChecklist.getSmoke() != null)
            this.smoke = newChecklist.getSmoke();
    }
}
