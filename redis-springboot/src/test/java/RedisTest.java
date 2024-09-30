import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.study.RedisSpringBoot;
import com.study.pojo.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

/**
 * @Author Ryan Yan
 * @Since 2021/2/4 16:45
 */

//@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = RedisSpringBoot.class)
public class RedisTest {

    @Autowired
    private RedisTemplate redisTemplate;


    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Test
    public void test1(){
        //ops: operation操作的意思
        //操作字符串类型
        ValueOperations valueOperations = redisTemplate.opsForValue();
        valueOperations.set("key1","1");
        String key1 = (String) valueOperations.get("key1");
        System.out.println(key1);


        //List类型
        redisTemplate.opsForList().leftPush("list2","aaaa");
        List list2 = redisTemplate.opsForList().range("list2", 0, -1);
        System.out.println(list2);
    }


    @Test
    public void test2(){
        //获取reids的连接对象
        RedisConnection connection = redisTemplate.getConnectionFactory().getConnection();

    }


    /**
     * 保存对象信息
     * 通常都是需要自己配置RedisTemplate 的序列化
     */
    @Test
    public void test3(){

        User user = new User("yyf", 12);
        //转json 字符串
        String userJsonString = null;
        try {
            userJsonString = new ObjectMapper().writeValueAsString(user);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        //保存json字符串
        redisTemplate.opsForValue().set("user:1",userJsonString);
        System.out.println(redisTemplate.opsForValue().get("user:1"));
//        stringRedisTemplate.opsForValue().set("user",userJsonString);
//        System.out.println(stringRedisTemplate.opsForValue().get("user"));


        //若直接保存对象，则对象需要序列化
        redisTemplate.opsForValue().set("user2:1",user);
        System.out.println(redisTemplate.opsForValue().get("user2:1"));

    }

}
