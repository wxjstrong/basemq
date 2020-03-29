package producer;

/*
 * Created by wxj on 2020/3/13 0013 8:49
 */

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.wxj.rbmq.utils.RbConnectionUtil;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class SimpleProducer {
     //定义队列名
    private static final String QUEUE_NAME="test_queue";

    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = RbConnectionUtil.getConnection();

        System.out.println(connection.isOpen());

        Channel channel = connection.createChannel();

        channel.queueDeclare(QUEUE_NAME,false,false,false,null);

        String msg = "测试简单模式消息";

        System.out.println("生产者发送消息"+msg);

        channel.basicPublish("",QUEUE_NAME,null,msg.getBytes());

        channel.close();

        connection.close();

    }
}
