package com.gdsc.dorm.checklist;

import com.gdsc.dorm.checklist.data.dto.req.MakeMateChecklistReq;
import com.gdsc.dorm.checklist.data.dto.req.MakeMemberChecklistReq;
import com.gdsc.dorm.checklist.data.dto.res.GetMateChecklistRes;
import com.gdsc.dorm.checklist.data.dto.res.GetMemberChecklistRes;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/checklist")
@RequiredArgsConstructor
public class ChecklistController {
    private final ChecklistService checklistService;
    private final String AUTHORIZATION_HEADER = "Authorization";

    @PostMapping("/member")
    public ResponseEntity<GetMemberChecklistRes> makeMemberChecklist(@RequestHeader(AUTHORIZATION_HEADER) String authorizationHeader, @RequestBody MakeMemberChecklistReq req) {
        return checklistService.makeMemberChecklist(authorizationHeader, req);
    }

    @PostMapping("/mate")
    public ResponseEntity<GetMateChecklistRes> makeMateChecklist(@RequestHeader(AUTHORIZATION_HEADER) String authorizationHeader, @RequestBody MakeMateChecklistReq req) {
        return checklistService.makeMateChecklist(authorizationHeader, req);
    }

    @PatchMapping("/member")
    public ResponseEntity<GetMemberChecklistRes> updateMemberChecklist(@RequestHeader(AUTHORIZATION_HEADER) String authorizationHeader, @RequestBody MakeMemberChecklistReq req) {
        return checklistService.updateMemberChecklist(authorizationHeader, req);
    }

    @PatchMapping("/mate")
    public ResponseEntity<GetMateChecklistRes> updateMateChecklist(@RequestHeader(AUTHORIZATION_HEADER) String authorizationHeader, @RequestBody MakeMateChecklistReq req) {
        return checklistService.updateMateChecklist(authorizationHeader, req);
    }

    @GetMapping("/member")
    public ResponseEntity<GetMemberChecklistRes> getMemberChecklist(@RequestHeader(AUTHORIZATION_HEADER) String authorizationHeader, @RequestParam(required = false) Long memberId) {
        return checklistService.getMemberChecklist(authorizationHeader, memberId);
    }

    @GetMapping("/mate")
    public ResponseEntity<GetMateChecklistRes> getMateChecklist(@RequestHeader(AUTHORIZATION_HEADER) String authorizationHeader, @RequestParam(required = false) Long memberId) {
        return checklistService.getMateChecklist(authorizationHeader, memberId);
    }
}
