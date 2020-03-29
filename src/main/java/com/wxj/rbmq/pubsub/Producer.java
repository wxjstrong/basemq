package com.wxj.rbmq.pubsub;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.wxj.rbmq.utils.RbConnectionUtil;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Producer {
    //命名一个exchange
    private static  final String EXCHANGE_NAME="fanout_exchange";

    /**
     * 使用fanout的exchange来实验
     * @param args
     */
    public static void main(String[] args) throws IOException, TimeoutException {
        System.out.println("pubsub_fanout_exchange 生产者已经启动……");

        //获取连接
        Connection conn = RbConnectionUtil.getConnection();

        //创建连接通道
        Channel channel = conn.createChannel();

        //声明绑定交换机  参数1交换机名称，参数2 交换机类型
        channel.exchangeDeclare(EXCHANGE_NAME,"fanout");
        //channel.exchangeBind()
        String msg = "pubsub_fanout_exchange";
        //发送消息
        channel.basicPublish(EXCHANGE_NAME,"",null,msg.getBytes());


    }
}
