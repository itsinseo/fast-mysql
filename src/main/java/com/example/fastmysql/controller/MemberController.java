package com.example.fastmysql.controller;

import com.example.fastmysql.domain.member.dto.MemberDto;
import com.example.fastmysql.domain.member.dto.MemberNicknameHistoryDto;
import com.example.fastmysql.domain.member.dto.RegisterMemberCommand;
import com.example.fastmysql.domain.member.service.MemberReadService;
import com.example.fastmysql.domain.member.service.MemberWriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/members")
public class MemberController {
    final private MemberWriteService memberWriteService;
    final private MemberReadService memberReadService;

    @PostMapping
    public MemberDto register(@RequestBody RegisterMemberCommand command) {
        var member = memberWriteService.register(command);
        return memberReadService.toDto(member);
    }

    @GetMapping("/{memberId}")
    public MemberDto getMember(@PathVariable Long memberId) {
        return memberReadService.getMember(memberId);
    }

    @PostMapping("/{memberId}/nickname")
    public MemberDto changeNickname(@PathVariable Long memberId, @RequestBody String nickname) {
        memberWriteService.changeNickname(memberId, nickname);
        return memberReadService.getMember(memberId);
    }

    @GetMapping("/{memberId}/nickname-histories")
    public List<MemberNicknameHistoryDto> getNicknameHistories(@PathVariable Long memberId) {
        return memberReadService.getNicknameHistories(memberId);
    }
}
