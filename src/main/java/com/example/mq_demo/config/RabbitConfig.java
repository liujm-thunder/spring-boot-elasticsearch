package com.example.mq_demo.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//
//@Configuration
//@Slf4j
public class RabbitConfig {
    // 添加json格式序列化器
//    @Bean
//    public MessageConverter messageConverter(){
//        return new Jackson2JsonMessageConverter();
//    }
//
//    // 创建普通交换机
//    @Bean
//    public Exchange normalExchange(){
//        return ExchangeBuilder.directExchange("normal.exchange").durable(true).build();
//    }
//
//    // 创建普通队列，设置ttl为5秒，绑定死信交换机
//    @Bean
//    public Queue normalQueue(){
//        return QueueBuilder.durable("normal.queue").ttl(5000)
//                .deadLetterExchange("dead.letter.exchange").deadLetterRoutingKey("dead").build();
//    }
//
//    // 创建普通交换机和普通队列的绑定关系
//    @Bean
//    public Binding normalBinding(@Qualifier("normalExchange") Exchange exchange, @Qualifier("normalQueue") Queue queue){
//        return BindingBuilder.bind(queue).to(exchange).with("normal").noargs();
//    }
//
//    // 创建死信交换机
//    @Bean
//    public Exchange deadLetterExchange(){
//        return ExchangeBuilder.directExchange("dead.letter.exchange").durable(true).build();
//    }
//
//    // 创建死信队列
//    @Bean
//    public Queue deadLetterQueue(){
//        return QueueBuilder.durable("dead.letter.queue").build();
//    }
//
//    // 创建普通交换机和普通队列的绑定关系
//    @Bean
//    public Binding deadLetterBinding(@Qualifier("deadLetterExchange") Exchange exchange, @Qualifier("deadLetterQueue") Queue queue){
//        return BindingBuilder.bind(queue).to(exchange).with("dead").noargs();
//    }
}

