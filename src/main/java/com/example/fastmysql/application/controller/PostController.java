package com.example.fastmysql.application.controller;

import com.example.fastmysql.domain.post.dto.DailyPostCount;
import com.example.fastmysql.domain.post.dto.DailyPostCountRequest;
import com.example.fastmysql.domain.post.dto.PostCommand;
import com.example.fastmysql.domain.post.entity.Post;
import com.example.fastmysql.domain.post.service.PageCursor;
import com.example.fastmysql.domain.post.service.PostReadService;
import com.example.fastmysql.domain.post.service.PostWriteService;
import com.example.fastmysql.util.CursorRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/posts")
public class PostController {

    private final PostWriteService postWriteService;
    private final PostReadService postReadService;

    @PostMapping("")
    public Long create(@RequestBody PostCommand command) {
        return postWriteService.create(command);
    }


    @GetMapping("/daily-post-counts")
    public List<DailyPostCount> getDailyPostCounts(DailyPostCountRequest request) {
        return postReadService.getDailyPostCount(request);
    }

    @GetMapping("/members/{memberId}")
    public Page<Post> getPosts(
            @PathVariable Long memberId,
            Pageable pageable) {
        return postReadService.getPosts(memberId, pageable);
    }

    @GetMapping("/members/{memberId}/by-cursor")
    public PageCursor<Post> getPosts(
            @PathVariable Long memberId,
            CursorRequest cursorRequest) {
        return postReadService.getPosts(memberId, cursorRequest);
    }
}
