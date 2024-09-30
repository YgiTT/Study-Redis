package com.study;

import redis.clients.jedis.Jedis;

/**
 * 连接 redis
 * @Author Ryan Yan
 * @Since 2021/2/4 11:25
 */
public class TestConnect {

    public static final String IP =  "10.87.60.68";

    public static final Integer PORT = 6379;

    public static void main(String[] args) {
        //创建连接
//        Jedis jedis = new Jedis("localhost", 6379);
        Jedis jedis = new Jedis(IP, PORT);
        System.out.println(jedis.ping());
        //关闭连接
        jedis.close();
    }


}
