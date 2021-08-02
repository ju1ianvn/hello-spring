package com.example.hellospring

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import static org.assertj.core.api.Assertions.*

@SpringBootTest
class SmokeTests {
    @Autowired
    private HomeController controller

    @Test
    void contextLoad() {
        assertThat(controller).isNotNull()
    }
}
