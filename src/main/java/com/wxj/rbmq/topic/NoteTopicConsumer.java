package com.wxj.rbmq.topic;

import com.rabbitmq.client.*;
import com.wxj.rbmq.utils.RbConnectionUtil;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class NoteTopicConsumer {

    private static final String EXCHANGE_NAME = "topic_exchange";

    private static final String QUEUE_NAME="topic_note";

    public static void main(String[] args) throws IOException, TimeoutException {
        System.out.println("邮件消费者已经启动……");
        //创建连接
        Connection conn = RbConnectionUtil.getConnection();

        Channel channel = conn.createChannel();

        channel.queueDeclare(QUEUE_NAME,false,false,false,null);

        channel.queueBind(QUEUE_NAME,EXCHANGE_NAME,"msg.*.*");

        DefaultConsumer consumer = new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String msg = new String(body,"UTF-8");

                System.out.println("短信消费者受到消息为"+msg);
            }
        };

        channel.basicConsume(QUEUE_NAME,true,consumer);
    }

}
