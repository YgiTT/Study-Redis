package com.practice;

import com.study.TestConnect;
import redis.clients.jedis.Jedis;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * 实践-手机验证码过期
 * @Author Ryan Yan
 * @Since 2021/5/7 11:01
 */
public class PhoneCode {

    public static void main(String[] args) {
        //获得验证码
        String code = getCode();
        //手机添加验证码
        addCode("12345678912",code);

        //睡
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //手机验证验证码
        verifyCode("12345678912",code);
    }

    /**
     *手机验证验证码
     */
    public static void verifyCode(String phone,String verifyCode){
        //连接redis
        Jedis jedis = new Jedis(TestConnect.IP, TestConnect.PORT);
        //手机验证码key
        String codekey = String.format("VerifyCode%s:code", phone);
        //获取redis对应的验证码
        String code = jedis.get(codekey);
        if(code == null){
            System.out.println("验证码已过期");
        }else if (code.equals(verifyCode)){
            System.out.println("验证成功");
        }else {
            System.out.println("验证码不正确");
        }
        jedis.close();
    }

    /**
     * 2.每个手机每天只能发送3次验证码，验证码放到redis并设置过期时间
     * @param phone
     * @param code
     */
    public static void addCode(String phone, String code){
        //连接redis
        Jedis jedis = new Jedis(TestConnect.IP, TestConnect.PORT);

        //拼接key
        //手机发送次数key
        String countKey = String.format("VerifyCode%s:count", phone);
        //手机验证码key
        String codekey = String.format("VerifyCode%s:code", phone);

        //手机每天只能发送3次证码
        String count = jedis.get(countKey);
        if(count == null){
            //第一次发送,设置发送次数为1,key过期时间为24小时
            jedis.setex(countKey,24*60*60,"1");
        }else if(Integer.parseInt(count) <= 2){
            //发送次数+1
            jedis.incr(countKey);
        }else if(Integer.parseInt(count) == 3){
            //已发送3次
            System.out.println("今天已经发送3次验证码，不能再发送");
            jedis.close();
            return;
        }

        //设置验证码,过期时间秒
        jedis.setex(codekey,10,code);
        jedis.close();

    }


    /**
     * 1.生成6个随机数的验证码
     * @return
     */
    public static String getCode(){
        Random random = new Random();
        String code = "";
        for (int i = 0; i < 6; i++) {
            //获得[0,10)区间的随机数
            int rand = random.nextInt(10);
            //拼接
            code += rand;
        }
        return code;

    }

}
