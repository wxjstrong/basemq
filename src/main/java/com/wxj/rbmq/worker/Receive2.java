package com.wxj.rbmq.worker;

import com.rabbitmq.client.*;
import com.wxj.rbmq.utils.RbConnectionUtil;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Receive2 {
    private  final  static String QUEUE_NAME ="test_queue_work";

    public static void main(String[] args) throws IOException, TimeoutException {
        System.out.println("第2消费者启动");
        //创建连接
        Connection conn = RbConnectionUtil.getConnection();
        //获取通道
        final Channel channel = conn.createChannel();
        //绑定队列
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);

        channel.basicQos(1);

        DefaultConsumer defaultConsumer = new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String msgString = new  String(body,"UTF-8");
                System.out.println("消费者2获取消息"+msgString);
                    //手动回执消息
                    channel.basicAck(envelope.getDeliveryTag(),true);
            }

        };

        channel.basicConsume(QUEUE_NAME,false,defaultConsumer);

    }
}
