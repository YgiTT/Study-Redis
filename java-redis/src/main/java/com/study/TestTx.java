package com.study;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

/**
 * redis事务
 * @Author Ryan Yan
 * @Since 2021/2/4 14:44
 */
public class TestTx {
    public static void main(String[] args) {
        Jedis jedis = new Jedis("localhost", 6379);
        //开启 事务
        Transaction multi = jedis.multi();
        try {
            multi.set("user3:1:name","yyf");
            multi.set("user3:1:age","18");
            //执行 事务
            multi.exec();
        }catch (Exception e){
            //取消事务
            multi.discard();
            e.printStackTrace();
        }
        finally {
            jedis.close();
        }
    }
}
