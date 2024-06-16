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

    private Long getMemberIdFromHeader(String header) {
        String accessToken = tokenProvider.getAccessToken(header);
        return tokenProvider.getUserId(accessToken);
    }

    @Transactional
    public ResponseEntity<GetMemberChecklistRes> makeMemberChecklist(String header, MakeMemberChecklistReq req) {
        Long memberId = getMemberIdFromHeader(header);

        UserChecklist checklist = req.toEntity();
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));

        userChecklistRepository.save(checklist);
        member.updateMemberChecklist(checklist);

        return new ResponseEntity<>(new GetMemberChecklistRes(checklist), HttpStatus.CREATED);
    }

    @Transactional
    public ResponseEntity<GetMateChecklistRes> makeMateChecklist(String header, MakeMateChecklistReq req) {
        Long memberId = getMemberIdFromHeader(header);

        MateChecklist checklist = req.toEntity();
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));

        mateChecklistRepository.save(checklist);
        member.updateMateChecklist(checklist);

        return new ResponseEntity<>(new GetMateChecklistRes(checklist), HttpStatus.CREATED);
    }

    @Transactional
    public ResponseEntity<GetMemberChecklistRes> updateMemberChecklist(String header, MakeMemberChecklistReq req) {
        Long memberId = getMemberIdFromHeader(header);

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));

        UserChecklist existingChecklist = member.getUserChecklist();
        existingChecklist.update(req.toEntity());

        return new ResponseEntity<>(new GetMemberChecklistRes(existingChecklist), HttpStatus.ACCEPTED);
    }

    @Transactional
    public ResponseEntity<GetMateChecklistRes> updateMateChecklist(String header, MakeMateChecklistReq req) {
        Long memberId = getMemberIdFromHeader(header);

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));

        MateChecklist existingChecklist = member.getMateChecklist();
        existingChecklist.update(req.toEntity());

        return new ResponseEntity<>(new GetMateChecklistRes(existingChecklist), HttpStatus.ACCEPTED);
    }
}
