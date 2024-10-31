package com.wlkg.sms;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class test {
    @Autowired
    private AmqpTemplate amqpTemplate;

    @Test
    public void sendTest()
    {
        Map<String,String> map = new HashMap<>();
        map.put("phone", "17612168142");
        map.put("code","789123");
        amqpTemplate.convertAndSend("wlkg.sms.exchange","sms.verify.code",map);
        try {
            Thread.sleep(10000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
