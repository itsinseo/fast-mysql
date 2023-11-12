package com.example.fastmysql.domain.follow.service;

import com.example.fastmysql.domain.follow.entity.Follow;
import com.example.fastmysql.domain.follow.repository.FollowRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class FollowReadService {
    private final FollowRepository followRepository;

    public List<Follow> getFollowings(Long memberId) {
        return followRepository.findAllByFromMemberId(memberId);
    }
}
