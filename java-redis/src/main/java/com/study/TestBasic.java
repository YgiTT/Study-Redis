package com.study;

import redis.clients.jedis.Jedis;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * redis 基本操作
 * @Author Ryan Yan
 * @Since 2021/5/6 16:57
 */
public class TestBasic {


    public static void main(String[] args) {
        keyTest();
        System.out.println("===================");
        stringTest();
        System.out.println("===================");
        listTest();
        System.out.println("===================");
        setTest();
        System.out.println("===================");
        hashTest();
        System.out.println("==================");
        zsetTest();
    }

    /**
     * key操作
     */
    public static void keyTest(){
        Jedis jedis = new Jedis(TestConnect.IP, TestConnect.PORT);

        jedis.set("k1", "v1");
        jedis.set("k2", "v2");
        jedis.set("k3", "v3");

        //获取所有key
        Set<String> keys = jedis.keys("*");
        for (String key : keys) {
            System.out.println(key);
        }
        //获取key的数量
        System.out.println(keys.size());
        //exists
        System.out.println(jedis.exists("k1"));
        //TTL
        System.out.println(jedis.ttl("k1"));

        jedis.close();
    }

    /**
     * String 操作
     */
    public static void stringTest(){
        Jedis jedis = new Jedis(TestConnect.IP, TestConnect.PORT);
        //添加//
        //set
        jedis.set("k1", "v1");
        //mset
        jedis.mset("k1","v11","k2","v2","k3","v3");

        //获取//
        List<String> mget = jedis.mget("k1", "k2", "k3");
        System.out.println(mget);

        jedis.close();
    }

    /**
     * list 操作
     */
    public static void listTest(){
        Jedis jedis = new Jedis(TestConnect.IP, TestConnect.PORT);

        jedis.lpush("key1", "a","b","c");

        List<String> values = jedis.lrange("key1",0,-1);
        System.out.println(values);

        jedis.close();
    }

    /**
     * Set 操作
     */
    public static void setTest(){
        Jedis jedis = new Jedis(TestConnect.IP, TestConnect.PORT);

        jedis.sadd("name","ryan","juming","jonojon","dapeng","di");

        Set<String> name = jedis.smembers("name");
        System.out.println(name);

        jedis.close();
    }

    /**
     * Hash 操作
     */
    public  static void hashTest(){
        Jedis jedis = new Jedis(TestConnect.IP, TestConnect.PORT);

        jedis.hset("user","name","yyf");
        jedis.hset("user","age","18");

        Map<String, String> user = jedis.hgetAll("user");

        System.out.println(user);

        jedis.close();
    }

    /**
     * Zset 操作
     */
    public static void zsetTest(){
        Jedis jedis = new Jedis(TestConnect.IP, TestConnect.PORT);

        jedis.zadd("china",100d,"shanghai");
        jedis.zadd("china",105d,"guangzhou");

        Set<String> china = jedis.zrevrangeByScore("china", 200, 0);
        System.out.println(china);

        jedis.close();
    }
}
