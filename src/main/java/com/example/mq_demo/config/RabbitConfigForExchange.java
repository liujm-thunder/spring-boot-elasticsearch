package com.example.mq_demo.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class RabbitConfigForExchange {
    // 添加json格式序列化器
    @Bean
    public MessageConverter messageConverter(){
        return new Jackson2JsonMessageConverter();
    }

    // 创建延时交换机
    @Bean
    public Exchange delayExchange(){
        return ExchangeBuilder.directExchange("delay.exchange").delayed().durable(true).build();
    }

    // 创建队列
    @Bean
    public Queue delayQueue(){
        return QueueBuilder.durable("delay.queue").build();
    }

    // 创建延时交换机和队列的绑定关系
    @Bean
    public Binding delayBinding(@Qualifier("delayExchange") Exchange exchange, @Qualifier("delayQueue") Queue queue){
        return BindingBuilder.bind(queue).to(exchange).with("delay").noargs();
    }
}

