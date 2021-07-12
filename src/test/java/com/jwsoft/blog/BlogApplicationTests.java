package com.jwsoft.blog;

import com.jwsoft.blog.test.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;


class BlogApplicationTests {
    @Test
    public void create() {
        Member member = new Member(1, null, null, null);

        Assertions.assertThat(member).isEqualTo(null);
    }
}
