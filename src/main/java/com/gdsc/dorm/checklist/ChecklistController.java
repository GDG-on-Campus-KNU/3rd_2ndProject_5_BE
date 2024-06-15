package com.gdsc.dorm.checklist;

import com.gdsc.dorm.checklist.data.dto.req.MakeMateChecklistReq;
import com.gdsc.dorm.checklist.data.dto.req.MakeMemberChecklistReq;
import com.gdsc.dorm.checklist.data.dto.res.GetMateChecklistRes;
import com.gdsc.dorm.checklist.data.dto.res.GetMemberChecklistRes;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/checklist")
@RequiredArgsConstructor
public class ChecklistController {
    private final ChecklistService checklistService;

    @PostMapping("/member")
    public ResponseEntity<GetMemberChecklistRes> makeMemberChecklist(@RequestHeader("Authorization") String authorizationHeader, @RequestBody MakeMemberChecklistReq req) {
        return checklistService.makeMemberChecklist(authorizationHeader, req);
    }

    @PostMapping("/mate")
    public ResponseEntity<GetMateChecklistRes> makeMateChecklist(@RequestHeader("Authorization") String authorizationHeader, @RequestBody MakeMateChecklistReq req) {
        return checklistService.makeMateChecklist(authorizationHeader, req);
    }
}
