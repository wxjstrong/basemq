package com.wxj.rbmq.worker;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.wxj.rbmq.utils.RbConnectionUtil;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Send {

    private  final  static String QUEUE_NAME ="test_queue_work";

    public static void main(String[] args) throws IOException, TimeoutException {
        //获取连接工厂-->获取连接
        Connection conn = RbConnectionUtil.getConnection();
        //创建通道
        Channel channel = conn.createChannel();
        //声明队列
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);
        channel.basicQos(1);

        for (int i=0;i<50;i++){
            String msg = QUEUE_NAME+"_"+i;
            System.out.println("生产者发送消息"+msg);
            channel.basicPublish("",QUEUE_NAME,null,msg.getBytes());
        }

        channel.close();
        conn.close();
    }
}
