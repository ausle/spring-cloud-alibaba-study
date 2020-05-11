package com.asule;


import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;

@Slf4j
public class SentinelTest {



    @Test
    public void test(){
        RestTemplate restTemplate = new RestTemplate();
        for (int i = 0; i <100; i++) {
            String object = restTemplate.getForObject("http://localhost:8000/shares/test-a", String.class);
            log.info("====="+object+"=====");
        }




    }

}
