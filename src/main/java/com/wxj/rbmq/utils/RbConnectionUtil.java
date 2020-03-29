package com.wxj.rbmq.utils;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/*
 * Created by wxj on 2020/3/12 0012 8:58
 */
public class RbConnectionUtil {

    public static Connection getConnection() throws IOException, TimeoutException {
        //获取连接工厂
        ConnectionFactory connectionFactory = new ConnectionFactory();

        //
        Connection connection = connectionFactory.newConnection();

        return connection;
    }

    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = getConnection();
        System.out.println();
        connection.isOpen();
    }
}
