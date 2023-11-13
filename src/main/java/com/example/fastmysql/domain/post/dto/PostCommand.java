package com.example.fastmysql.domain.post.dto;

public record PostCommand(
        Long memberId,
        String contents
) {
}
