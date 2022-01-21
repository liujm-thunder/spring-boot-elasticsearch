package com.example.mq_demo.consumer;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalTime;

@Service
@RabbitListener(queues = "delay.queue")
public class ConsumerForExchange {

    @RabbitHandler
    public void getReleaseOrderMessage(Message message, String data, Channel channel) throws IOException {
        String line = "收到延时消息时间：" + LocalTime.now()+",order="+data;
        try {
            FileWriter writer = new FileWriter("/Users/liujianmeng/Downloads/consumer.txt",true);
            writer.write(line+"\n");
            writer.flush();//刷新内存，将内存中的数据立刻写出。
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(line);
        // 确认消息
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
    }

}
