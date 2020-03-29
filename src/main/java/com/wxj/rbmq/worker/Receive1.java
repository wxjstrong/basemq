package com.wxj.rbmq.worker;

import com.rabbitmq.client.*;
import com.wxj.rbmq.utils.RbConnectionUtil;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.concurrent.TimeoutException;

public class Receive1 {

    private  final  static String QUEUE_NAME ="test_queue_work";

    public static void main(String[] args) throws IOException, TimeoutException {
        System.out.println("第1消费者启动");
        //创建连接
        Connection conn = RbConnectionUtil.getConnection();
        //获取通道
        final Channel channel = conn.createChannel();
        //绑定队列
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);
        //basicQos参数是多少就是多少数据一块确认
       channel.basicQos(1);

       DefaultConsumer defaultConsumer = new DefaultConsumer(channel){
           @Override
           public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
               String msgString = new  String(body,"UTF-8");
               System.out.println("消费者1获取消息"+msgString);

               try {
                   Thread.sleep(2000);
               } catch (InterruptedException e) {
                   e.printStackTrace();
               }finally {
                   //手动回执消息 如果不设置手动应答或者自动应答，消费过的数据还会存在队列中，下次启动消费者还会再被消费
                    channel.basicAck(envelope.getDeliveryTag(),true);
               }
           }

        };
       //第二个参数是是否开放自动应答，true为开放，false为不开放自动应答
        channel.basicConsume(QUEUE_NAME,false,defaultConsumer);

    }
}
