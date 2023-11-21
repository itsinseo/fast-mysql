package com.example.fastmysql.domain.post.service;

import com.example.fastmysql.util.CursorRequest;

import java.util.List;

public record PageCursor<T>(
        CursorRequest nextCursorRequest,
        List<T> body
) {
}
