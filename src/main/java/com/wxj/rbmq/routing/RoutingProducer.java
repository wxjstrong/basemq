package com.wxj.rbmq.routing;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.wxj.rbmq.utils.RbConnectionUtil;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class RoutingProducer {



    //声明交换机
    private static String EXCHANGE_NAME ="direct_exchange";

    public static void main(String[] args) throws IOException, TimeoutException {
        System.out.println("routing_exchange 生产者已经启动……");
        //创建连接
        Connection conn = RbConnectionUtil.getConnection();
        //创建通道
        Channel channel = conn.createChannel();
        //绑定交换机
        channel.exchangeDeclare(EXCHANGE_NAME,"direct");
        //绑定routingkey
        String routingkeyemail = "email";
        String routingkeynote = "note";
        String msg ="routing_direct_exchange_msg"+routingkeyemail+"--------"+routingkeynote;
        channel.basicPublish(EXCHANGE_NAME,routingkeyemail,null,msg.getBytes());
        channel.basicPublish(EXCHANGE_NAME,routingkeynote,null,msg.getBytes());
        System.out.println("生产者发送的消息是"+msg);

    }
}
