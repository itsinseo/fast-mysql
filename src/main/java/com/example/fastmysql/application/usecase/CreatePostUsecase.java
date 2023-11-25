package com.example.fastmysql.application.usecase;

import com.example.fastmysql.domain.follow.entity.Follow;
import com.example.fastmysql.domain.follow.service.FollowReadService;
import com.example.fastmysql.domain.post.dto.PostCommand;
import com.example.fastmysql.domain.post.service.PostWriteService;
import com.example.fastmysql.domain.post.service.TimelineWriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CreatePostUsecase {
    private final PostWriteService postWriteService;
    private final FollowReadService followReadService;
    private final TimelineWriteService timelineWriteService;

    public Long execute(PostCommand postCommand) {
        var postId = postWriteService.create(postCommand);

        var followerIds = followReadService.getFollowers(postCommand.memberId())
                .stream()
                .map(Follow::getFromMemberId)
                .toList();

        timelineWriteService.deliveryToTimeline(postId, followerIds);

        return postId;
    }
}
