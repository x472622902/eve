package dayan.eve.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;

import java.util.HashMap;

/**
 * Created by xsg on 2/20/2017.
 */
@Configuration
@EnableCaching
public class RedisCacheConfig extends CachingConfigurerSupport {
    public final static String ONE_SECOND_CACHE = "oneSecondCache";
    public final static String ONE_MINUTE_CACHE = "oneMinuteCache";
    public final static String HALF_HOUR_CACHE = "halfHourCache";
    public final static String ONE_DAY_CACHE = "oneDayCache";
    public final static String ONE_WEEK_CACHE = "oneWeekCache";
//    private final EveProperties.Redis redis;
//
//    @Autowired
//    public RedisCacheConfig(EveProperties eveProperties) {
//        this.redis = eveProperties.getRedis();
//    }

    @Bean
    public KeyGenerator keyGenerator() {
        return (target, method, params) -> {
            StringBuilder sb = new StringBuilder();
            sb.append(target.getClass().getName());
            sb.append(method.getName());
            for (Object obj : params) {
                sb.append(obj.toString());
            }
            return sb.toString();
        };
    }

//    @Bean
//    public JedisConnectionFactory redisConnectionFactory() {
//        JedisConnectionFactory factory = new JedisConnectionFactory();
//        factory.setUsePool(true);
//        factory.setHostName(redis.getHost());
//        factory.setPort(redis.getPort());
//        factory.setTimeout(redis.getTimeout()); //设置连接超时时间
//        factory.afterPropertiesSet();
//        return factory;
//    }

    @Bean
    public CacheManager cacheManager(RedisTemplate redisTemplate) {
        RedisCacheManager cacheManager = new RedisCacheManager(redisTemplate);
        // Number of seconds before expiration. Defaults to unlimited (0)
        cacheManager.setDefaultExpiration(60 * 60 * 24L); //设置key-value超时时间
        cacheManager.setExpires(new HashMap<String, Long>() {{
            put(ONE_SECOND_CACHE, 1L);
            put(ONE_MINUTE_CACHE, 60L);
            put(HALF_HOUR_CACHE, 60 * 30L);
            put(ONE_DAY_CACHE, 60 * 60 * 24L);
            put(ONE_WEEK_CACHE, 7 * 60 * 60 * 24L);
        }});
        return cacheManager;
    }

    @Bean
    public RedisTemplate redisTemplate(JedisConnectionFactory factory) {
        StringRedisTemplate template = new StringRedisTemplate(factory);
        setSerializer(template);
//        template.afterPropertiesSet();
        return template;
    }

    private void setSerializer(StringRedisTemplate template) {
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Object.class);
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(om);
        template.setValueSerializer(jackson2JsonRedisSerializer);
        template.setHashKeySerializer(jackson2JsonRedisSerializer);
        template.setHashValueSerializer(jackson2JsonRedisSerializer);
    }
}
