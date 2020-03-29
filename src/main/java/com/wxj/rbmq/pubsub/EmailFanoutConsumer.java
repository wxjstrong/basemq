package com.wxj.rbmq.pubsub;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import com.wxj.rbmq.utils.RbConnectionUtil;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class EmailFanoutConsumer {

    private static final String QUEUE_NAME="fanout_email_queue";

    private  static  final String EXCHANGE_NAME = "fanout_exchange";

    public static void main(String[] args) throws IOException, TimeoutException {
        System.out.println("邮件消费者启动");

        //创建channel
        Channel channel = RbConnectionUtil.getConnection().createChannel();
        //消费者关联队列
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);
        //消费者绑定交换机 参数1队列 参数2 交换机 参数3 routingkey
        channel.queueBind(QUEUE_NAME,EXCHANGE_NAME,"");
       DefaultConsumer consumer = new DefaultConsumer(channel){
           @Override
           public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
              // super.handleDelivery(consumerTag, envelope, properties, body);
               String msg = new String(body,"UTF-8");
               System.out.println("邮件消费者获取到的消息为----->"+msg);
           }
       };

       //消费者监听队列消息
        channel.basicConsume(QUEUE_NAME,true,consumer);

    }
}
