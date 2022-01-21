package com.example.mq_demo.consumer;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalTime;

@Service
@RabbitListener(queues = "order.release.queue")
public class OrderReleaseQueueService {

    @RabbitHandler
    public void getReleaseOrderMessage(Message message, String data, Channel channel) throws IOException {
        System.out.println("收到延时消息时间：" + LocalTime.now()+",order="+data);
        // 确认消息
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
    }
}

