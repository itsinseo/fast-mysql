package com.example.fastmysql.domain.post.dto;

import java.time.LocalDate;

public record DailyPostCount(
        Long memberId,
        LocalDate date,
        Long postCount
) {
}
