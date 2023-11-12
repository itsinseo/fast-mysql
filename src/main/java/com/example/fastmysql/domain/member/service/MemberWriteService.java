package com.example.fastmysql.domain.member.service;

import com.example.fastmysql.domain.member.dto.RegisterMemberCommand;
import com.example.fastmysql.domain.member.entity.Member;
import com.example.fastmysql.domain.member.entity.MemberNicknameHistory;
import com.example.fastmysql.domain.member.repository.MemberNicknameHistoryRepository;
import com.example.fastmysql.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MemberWriteService {
    private final MemberRepository memberRepository;
    private final MemberNicknameHistoryRepository memberNicknameHistoryRepository;

    public Member register(RegisterMemberCommand command) {
        /*
            목표 - 회원정보(이메일, 닉네임, 생년월일)을 등록한다.
                - 닉네임은 10자를 넘길 수 없다.
            파라미터 - RegisterMemberCommand
            val member = Member.of(memberRegisterCommand)
            memberRepository.save()
         */
        var member = Member
                .builder()
                .nickname(command.nickname())
                .email(command.email())
                .birthday(command.birthday())
                .build();



        var savedMember =  memberRepository.save(member);
        saveNicknameHistory(savedMember);
        return savedMember;

    }

    public void changeNickname(Long memberId, String newNickname) {
        /*
            1. 회원의 닉네임을 변경
            2. 변경 내역을 저장
         */
        var member = memberRepository.findById(memberId).orElseThrow();
        member.changeNickname(newNickname);
        memberRepository.save(member);

        saveNicknameHistory(member);
    }

    private void saveNicknameHistory(Member member) {
        var history = MemberNicknameHistory
                .builder()
                .memberId(member.getId())
                .nickname(member.getNickname())
                .build();

        memberNicknameHistoryRepository.save(history);
    }
}
