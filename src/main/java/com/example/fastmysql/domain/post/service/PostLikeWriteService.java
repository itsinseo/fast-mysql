package com.example.fastmysql.domain.post.service;

import com.example.fastmysql.domain.member.dto.MemberDto;
import com.example.fastmysql.domain.post.entity.Post;
import com.example.fastmysql.domain.post.entity.PostLike;
import com.example.fastmysql.domain.post.repository.PostLikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PostLikeWriteService {

    private final PostLikeRepository postLikeRepository;

    public Long create(Post post, MemberDto memberDto) {
        var postLike = PostLike.builder()
                .memberId(memberDto.id())
                .postId(post.getId())
                .build();

        return postLikeRepository.save(postLike).getId();
    }
}