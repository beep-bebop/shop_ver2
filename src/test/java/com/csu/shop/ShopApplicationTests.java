package com.csu.shop;

import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@MapperScan("com.csu.shop.persistence")
class ShopApplicationTests {

    @Test
    void contextLoads() {

    }

}
