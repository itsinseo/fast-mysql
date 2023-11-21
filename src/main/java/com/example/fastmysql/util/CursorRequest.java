package com.example.fastmysql.util;

public record CursorRequest(
        Long key,
        Integer size
) {
    public static final Long NONE_KEY = -1L;

    public boolean hasKey() {
        return key != null;
    }

    public CursorRequest next(Long key) {
        return new CursorRequest(key, size);
    }
}
