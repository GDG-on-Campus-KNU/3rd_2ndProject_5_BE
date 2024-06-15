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
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChecklistService {
    private final MemberRepository memberRepository;
    private final TokenProvider tokenProvider;
    public ResponseEntity<GetMemberChecklistRes> makeMemberChecklist(String header, MakeMemberChecklistReq req) {
        Long memberId = getMemberIdFromHeader(header);

        UserChecklist checklist = req.toEntity();
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));

        member.updateMemberChecklist(checklist);

        return new ResponseEntity<>(new GetMemberChecklistRes(checklist), HttpStatus.CREATED);
    }

    public ResponseEntity<GetMateChecklistRes> makeMateChecklist(String header, MakeMateChecklistReq req) {
        Long memberId = getMemberIdFromHeader(header);

        MateChecklist checklist = req.toEntity();
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));

        member.updateMateChecklist(checklist);

        return new ResponseEntity<>(new GetMateChecklistRes(checklist), HttpStatus.CREATED);
    }
    private Long getMemberIdFromHeader(String header) {
        String accessToken = tokenProvider.getAccessToken(header);
        return tokenProvider.getUserId(accessToken);
    }
}
