package com.example.mq_demo;

import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

@SpringBootTest
class MqDemoApplicationTests {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Test
    void contextLoads() {
    }

//    @Test
//    public void testSendMessage() {
//        for (int i = 0; i < 5; i++) {
//            CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
//            rabbitTemplate.convertAndSend("normal.exchange", "normal",
//                    new User("baobao" + i, 18, new Date()), correlationData);
//        }
//    }

}
