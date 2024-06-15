package com.gdsc.dorm.checklist;

import com.gdsc.dorm.checklist.data.MateChecklist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MateChecklistRepository extends JpaRepository<MateChecklist, Long> {
}
