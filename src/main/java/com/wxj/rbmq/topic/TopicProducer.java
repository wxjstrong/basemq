package com.wxj.rbmq.topic;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.wxj.rbmq.utils.RbConnectionUtil;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class TopicProducer {

    //声明一个交换机
    private  static final String EXCHANGE_NAME="topic_exchange";

    public static void main(String[] args) throws IOException, TimeoutException {

        Connection conn = RbConnectionUtil.getConnection();

        Channel channel = conn.createChannel();

        channel.exchangeDeclare(EXCHANGE_NAME,"topic");

        String routingkey = "msg.topic.mytest";

        String msg = "测试发送topic消息";

        channel.basicPublish(EXCHANGE_NAME,routingkey,null,msg.getBytes());
        System.out.println("发送的消息是"+msg);






    }
}
