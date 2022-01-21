package com.example.mq_demo.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.mq_demo.product.Order;
import com.example.mq_demo.product.Product;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalTime;
import java.util.UUID;

@RestController
public class TestController {

    @Autowired
    private Product product;

//    @RequestMapping("/test")
    public String test(){
        for (int i = 0; i < 10; i++) {
            Order order = new Order();
            order.setId(i);
            order.setName("这是订单的名称啊，name="+ UUID.randomUUID());
            order.setDesc("这是订单描述信息，下单时间"+System.currentTimeMillis());
            product.sendMessage(order);
        }
        return "Hello world!";
    }


//    @RequestMapping("/test1")
    public String test1(){
        for (int i = 0; i < 10; i++) {
            Order order = new Order();
            order.setId(i);
            order.setName("这是订单的名称啊，name="+ UUID.randomUUID());
            order.setDesc("这是订单描述信息，下单时间"+System.currentTimeMillis());
            product.sendMessage2(order);
        }
        return "Hello world!";
    }


    /***
     * 将延时时间交给queue
     * 1、如果采用在消息属性上设置TTL而非使用队列TTL的方式，消息可能并不会按时死亡，
     *    因为RabbitMQ只会检查第1个消息是否过期，如果过期则丢到死信队列，如果第1个消息的延时时长很长，
     *    而第2个消息的延时时长很短，第2个消息并不会优先得到执行
     * 2、需要创建1个普通队列加1个对应的死信队列，创建的队列过多
     *
     */
    @RequestMapping("/test2")
    public String test2() throws InterruptedException {
        for (int i = 0; i < 3; i++) {
            Order order = new Order();
            order.setId(i);
            order.setName("这是订单的名称啊，name="+ UUID.randomUUID());
            order.setDesc("这是订单描述信息，下单时间"+System.currentTimeMillis());
            System.out.println("发送延时消息的时间：" + LocalTime.now()+",order="+ JSONObject.toJSONString(order));
            product.sendMessage3(order);
        }
        return "Hello world!";
    }


    /**
     * 将延时时间交给exchange
     * 需要安装 插件 rabbitmq_delayed_message_exchange
     *
     */
    @RequestMapping("/test3")
    public String test3()  {
        for (int i = 0; i < 1000000; i++) {
            Order order = new Order();
            order.setId(i);
            order.setName("这是订单的名称啊，name="+ UUID.randomUUID());
//            order.setDesc("这是订单描述信息，下单时间"+System.currentTimeMillis());
            product.sendMessage4(order,15000);
        }
        return "Hello world!";
    }
}
