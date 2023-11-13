package com.example.fastmysql.application.controller;

import com.example.fastmysql.domain.post.dto.DailyPostCount;
import com.example.fastmysql.domain.post.dto.DailyPostCountRequest;
import com.example.fastmysql.domain.post.dto.PostCommand;
import com.example.fastmysql.domain.post.service.PostReadService;
import com.example.fastmysql.domain.post.service.PostWriteService;
import lombok.RequiredArgsConstructor;
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
}
