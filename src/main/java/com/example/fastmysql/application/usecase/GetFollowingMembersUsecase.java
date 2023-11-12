package com.example.fastmysql.application.usecase;

import com.example.fastmysql.domain.follow.entity.Follow;
import com.example.fastmysql.domain.follow.service.FollowReadService;
import com.example.fastmysql.domain.member.dto.MemberDto;
import com.example.fastmysql.domain.member.service.MemberReadService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class GetFollowingMembersUsecase {

    private final MemberReadService memberReadService;
    private final FollowReadService followReadService;

    public List<MemberDto> execute(Long memberId) {
        var followings = followReadService.getFollowings(memberId);
        var followMemberIds = followings.stream().map(Follow::getToMemberId).toList();
        return memberReadService.getMembers(followMemberIds);
    }
}