package com.gdsc.dorm.checklist;

import com.gdsc.dorm.checklist.data.UserChecklist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserChecklistRepository extends JpaRepository<UserChecklist, Long> {
}
