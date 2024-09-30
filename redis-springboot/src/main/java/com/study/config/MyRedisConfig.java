package com.study.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * 自定义 redis配置
 * @Author Ryan Yan
 * @Since 2021/2/4 17:13
 */
@Configuration
public class MyRedisConfig {

    /**
     * 自定义 RedisTemplate
     * @param redisConnectionFactory
     * @return
     */
    @Bean
    public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<Object, Object> template = new RedisTemplate();
        template.setConnectionFactory(redisConnectionFactory);
        //序列化配置
        Jackson2JsonRedisSerializer<Object> serializer = new Jackson2JsonRedisSerializer<Object>(Object.class);
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        //key采用string的序列化
        template.setKeySerializer(stringRedisSerializer);
        //value 序列化采用json
        template.setValueSerializer(serializer);

        //
        template.afterPropertiesSet();
        return template;
    }


}
