package com.wxj.rbmq.routing;

import com.rabbitmq.client.*;
import com.wxj.rbmq.utils.RbConnectionUtil;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class RoutingNoteConsumer {

    private static final String EXCHANGE_NAME="direct_exchange";

    private static final String QUEUE_NAME="consumer_direct_note";

    public static void main(String[] args) throws IOException, TimeoutException {
        System.out.println("邮件消费者已经启动……");
        //创建连接
        Connection conn = RbConnectionUtil.getConnection();

        Channel channel = conn.createChannel();
        //消费者关联队列
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);
        //消费者绑定交换机 参数1 队列  参数2 交换机  参数3 routingkey
        channel.queueBind(QUEUE_NAME,EXCHANGE_NAME,"note");
        DefaultConsumer consumer = new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String msg = new String(body,"UTF-8");
                System.out.println("消费者获取的消息："+msg);
            }
        };
        //消费者监听队列消息
        channel.basicConsume(QUEUE_NAME,true,consumer);


    }
}
