package com.oauth2.demo;

import com.oauth2.demo.model.Oauth2Properties;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DemoApplicationTests {

    @Autowired
    Oauth2Properties oauth2Properties;
    @Test
    void contextLoads() {
        System.out.println(oauth2Properties.toString());
    }

}
