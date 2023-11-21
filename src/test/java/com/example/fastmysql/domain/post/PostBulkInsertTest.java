package com.example.fastmysql.domain.post;

import com.example.fastmysql.domain.post.entity.Post;
import com.example.fastmysql.domain.post.repository.PostRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.StopWatch;

import java.time.LocalDate;
import java.util.stream.IntStream;

@SpringBootTest
public class PostBulkInsertTest {

    @Autowired
    private PostRepository postRepository;

    @Test
    public void bulkInsert() {
        var easyRandom = PostFixtureFactory.get(
                3L,
                LocalDate.of(1950, 1, 1),
                LocalDate.of(2023, 11, 13)
        );

        var stopwatch = new StopWatch();
        stopwatch.start();

        var posts =IntStream.range(0, 3000000)
                .parallel()
                .mapToObj(i -> easyRandom.nextObject(Post.class))
                .toList();

        stopwatch.stop();
        System.out.println("객체 생성 시간 = " + stopwatch.getTotalTimeSeconds());

        var queryStopwatch = new StopWatch();
        queryStopwatch.start();
        postRepository.bulkInsert(posts);

        queryStopwatch.stop();
        System.out.println("DB 인서트 시간 = " + queryStopwatch.getTotalTimeSeconds());
    }
}