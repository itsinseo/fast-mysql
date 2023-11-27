package com.example.fastmysql.domain.post.service;

import com.example.fastmysql.domain.post.dto.DailyPostCount;
import com.example.fastmysql.domain.post.dto.DailyPostCountRequest;
import com.example.fastmysql.domain.post.dto.PostDto;
import com.example.fastmysql.domain.post.entity.Post;
import com.example.fastmysql.domain.post.repository.PostLikeRepository;
import com.example.fastmysql.domain.post.repository.PostRepository;
import com.example.fastmysql.util.CursorRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class PostReadService {

    private final PostRepository postRepository;
    private final PostLikeRepository postLikeRepository;

    public PostDto toDto(Post post) {
        return new PostDto(
                post.getId(),
                post.getContents(),
                post.getCreatedAt(),
                postLikeRepository.getCount(post.getId())
        );
    }

    public List<DailyPostCount> getDailyPostCount(DailyPostCountRequest request) {
        /*
            반환 값 -> 리스트 (작성 일자, 작성 회원의 id, 작성 게시물 개수)
            select createdDate, memberId, count(id)
            from Post
            where memberId = :memberId and createdDate between firstDate and lastDate
            group by createdDate, memberId
         */
        return postRepository.groupByCreatedDate(request);
    }

    public Post getPost(Long postId) {
        return postRepository.findById(postId, false).orElseThrow();
    }

    public Page<PostDto> getPosts(Long memberId, Pageable pageable) {
        return postRepository.findAllByMemberId(memberId, pageable).map(this::toDto);
    }

    public PageCursor<Post> getPosts(Long memberId, CursorRequest cursorRequest) {
        var posts = findAllBy(memberId, cursorRequest);
        var nextKey = getNextKey(posts);

        return new PageCursor<>(cursorRequest.next(nextKey), posts);
    }

    public PageCursor<Post> getPosts(List<Long> memberIds, CursorRequest cursorRequest) {
        var posts = findAllBy(memberIds, cursorRequest);
        var nextKey = getNextKey(posts);

        return new PageCursor<>(cursorRequest.next(nextKey), posts);
    }

    public List<Post> getPosts(List<Long> ids) {
        return postRepository.findAllByInId(ids);
    }

    private List<Post> findAllBy(Long memberId, CursorRequest cursorRequest) {
        if (cursorRequest.hasKey()) {
            return postRepository.findAllByLessThanIdAndMemberIdAndOrderByIdDesc(cursorRequest.key(), memberId, cursorRequest.size());
        } else {
            return postRepository.findAllByMemberIdAndOrderByIdDesc(memberId, cursorRequest.size());
        }
    }

    private List<Post> findAllBy(List<Long> memberIds, CursorRequest cursorRequest) {
        if (cursorRequest.hasKey()) {
            return postRepository.findAllByLessThanIdAndInMemberIdAndOrderByIdDesc(cursorRequest.key(), memberIds, cursorRequest.size());
        } else {
            return postRepository.findAllByInMemberIdAndOrderByIdDesc(memberIds, cursorRequest.size());
        }
    }

    private static long getNextKey(List<Post> posts) {
        return posts.stream()
                .mapToLong(Post::getId)
                .min()
                .orElse(CursorRequest.NONE_KEY);
    }
}
