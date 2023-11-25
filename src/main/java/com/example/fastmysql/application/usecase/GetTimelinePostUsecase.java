package com.example.fastmysql.application.usecase;

import com.example.fastmysql.domain.follow.entity.Follow;
import com.example.fastmysql.domain.follow.service.FollowReadService;
import com.example.fastmysql.domain.post.entity.Post;
import com.example.fastmysql.domain.post.service.PageCursor;
import com.example.fastmysql.domain.post.service.PostReadService;
import com.example.fastmysql.util.CursorRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class GetTimelinePostUsecase {

    private final PostReadService postReadService;
    private final FollowReadService followReadService;

    public PageCursor<Post> execute(Long memberId, CursorRequest cursorRequest) {
        var followings = followReadService.getFollowings(memberId);
        var followIds = followings.stream().map(Follow::getToMemberId).toList();
        return postReadService.getPosts(followIds, cursorRequest);
    }
}
