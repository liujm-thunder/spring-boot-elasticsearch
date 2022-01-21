package com.example.mq_demo.product;

import com.alibaba.fastjson.JSONObject;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalTime;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Component
public class Product {

    private final static String EXCHANGE = "exchange-test";

    private final static String ROUTING_KEY = "my_routing_key";

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sendMessage4(Order order,int delay) {
        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
        rabbitTemplate.convertAndSend("delay.exchange", "delay", JSONObject.toJSONString(order),message -> {
            message.getMessageProperties().setDelay(delay);
            return message;
        });
        String line = "发送完毕：" + LocalTime.now()+",order="+ JSONObject.toJSONString(order);
        try {
            FileWriter writer = new FileWriter("/Users/liujianmeng/Downloads/producer.txt",true);
            writer.write(line+"\n");
            writer.flush();//刷新内存，将内存中的数据立刻写出。
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(line);
    }


    public void sendMessage3(Order order) throws InterruptedException {
        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
        rabbitTemplate.convertAndSend("order.event.exchange", "order.create", JSONObject.toJSONString(order),correlationData);
        TimeUnit.SECONDS.sleep(3);
    }

    public void sendMessage2(Order order){
        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
        rabbitTemplate.convertAndSend("normal.exchange", "normal", JSONObject.toJSONString(order),correlationData);
    }

    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(new Jackson2JsonMessageConverter());
        return factory;
    }



    final RabbitTemplate.ConfirmCallback confirmCallback = (correlationData, ack, cause) -> {
        System.err.println("correlationData: " + correlationData);
        String messageId = correlationData.getId();
        if(ack){
            //如果confirm返回成功 则进行更新
            System.out.println("如果confirm返回成功 则进行更新。messageId="+messageId);
        } else {
            //失败则进行具体的后续操作:重试 或者补偿等手段
            System.err.println("异常处理...");
        }
    };

    public void sendMessage(Order order){
//        rabbitTemplate.setConfirmCallback(confirmCallback);
        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
        rabbitTemplate.convertAndSend(EXCHANGE,ROUTING_KEY, JSONObject.toJSONString(order),correlationData);
    }

}
