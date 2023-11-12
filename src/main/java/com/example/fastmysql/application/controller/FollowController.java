package com.example.fastmysql.application.controller;

import com.example.fastmysql.application.usecase.CreateFollowMemberUsecase;
import com.example.fastmysql.application.usecase.GetFollowingMembersUsecase;
import com.example.fastmysql.domain.member.dto.MemberDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/follow")
public class FollowController {

    private final CreateFollowMemberUsecase createFollowMemberUsecase;
    private final GetFollowingMembersUsecase getFollowingMembersUsecase;

    @PostMapping("/{fromMemberId}/{toMemberId}")
    public void create(@PathVariable Long fromMemberId, @PathVariable Long toMemberId) {
        createFollowMemberUsecase.execute(fromMemberId, toMemberId);
    }

    @GetMapping("/members/{fromMemberId}/followings")
    public List<MemberDto> getFollowings(@PathVariable Long fromMemberId) {
        return getFollowingMembersUsecase.execute(fromMemberId);
    }
}
