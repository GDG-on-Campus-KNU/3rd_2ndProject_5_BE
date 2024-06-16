package com.gdsc.dorm.checklist;

import com.gdsc.dorm.checklist.data.MateChecklist;
import com.gdsc.dorm.checklist.data.UserChecklist;
import com.gdsc.dorm.checklist.data.dto.req.MakeMateChecklistReq;
import com.gdsc.dorm.checklist.data.dto.req.MakeMemberChecklistReq;
import com.gdsc.dorm.checklist.data.dto.res.GetMateChecklistRes;
import com.gdsc.dorm.checklist.data.dto.res.GetMemberChecklistRes;
import com.gdsc.dorm.config.jwt.TokenProvider;
import com.gdsc.dorm.exception.CustomException;
import com.gdsc.dorm.exception.ErrorCode;
import com.gdsc.dorm.member.MemberRepository;
import com.gdsc.dorm.member.data.Member;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChecklistService {
    private final MemberRepository memberRepository;
    private final UserChecklistRepository userChecklistRepository;
    private final MateChecklistRepository mateChecklistRepository;
    private final TokenProvider tokenProvider;

    private Member getMemberFromHeader(String header) {
        String accessToken = tokenProvider.getAccessToken(header);
        Long memberId = tokenProvider.getUserId(accessToken);

        return memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));
    }

    @Transactional
    public ResponseEntity<GetMemberChecklistRes> makeMemberChecklist(String header, MakeMemberChecklistReq req) {
        UserChecklist checklist = req.toEntity();
        Member member = getMemberFromHeader(header);

        userChecklistRepository.save(checklist);
        member.updateMemberChecklist(checklist);

        return new ResponseEntity<>(new GetMemberChecklistRes(checklist), HttpStatus.CREATED);
    }

    @Transactional
    public ResponseEntity<GetMateChecklistRes> makeMateChecklist(String header, MakeMateChecklistReq req) {
        MateChecklist checklist = req.toEntity();
        Member member = getMemberFromHeader(header);

        mateChecklistRepository.save(checklist);
        member.updateMateChecklist(checklist);

        return new ResponseEntity<>(new GetMateChecklistRes(checklist), HttpStatus.CREATED);
    }

    @Transactional
    public ResponseEntity<GetMemberChecklistRes> updateMemberChecklist(String header, MakeMemberChecklistReq req) {
        Member member = getMemberFromHeader(header);

        UserChecklist existingChecklist = member.getUserChecklist();
        existingChecklist.update(req.toEntity());

        return new ResponseEntity<>(new GetMemberChecklistRes(existingChecklist), HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<GetMateChecklistRes> updateMateChecklist(String header, MakeMateChecklistReq req) {
        Member member = getMemberFromHeader(header);

        MateChecklist existingChecklist = member.getMateChecklist();
        existingChecklist.update(req.toEntity());

        return new ResponseEntity<>(new GetMateChecklistRes(existingChecklist), HttpStatus.OK);
    }
}
