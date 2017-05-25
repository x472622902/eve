package dayan.eve.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.guava.GuavaCacheManager;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.HashMap;

/**
 * spring cache，默认使用redis缓存，当缓存数据无状态，且数据较大时，建议指定cacheManager
 * 为guavaCacheManager，使用基于jvm的缓存
 * @Cacheable(value = "sortedSchool", cacheManager = "guavaCacheManager")
 * @author dengqg
 */
@Configuration
@EnableCaching
public class CacheConfig extends CachingConfigurerSupport {
    public final static String ONE_SECOND_CACHE = "oneSecondCache";
    public final static String ONE_MINUTE_CACHE = "oneMinuteCache";
    public final static String HALF_HOUR_CACHE = "halfHourCache";
    public final static String ONE_DAY_CACHE = "oneDayCache";


    private final RedisTemplate redisTemplate;

    @Autowired
    public CacheConfig(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     * 方法参数需要重写toString()或者hashCode方法
     *
     * @return key生成器
     */
    @Bean
    public KeyGenerator keyGenerator() {
        return ((target, method, params) -> {
            StringBuilder sb = new StringBuilder();
            sb.append(target.getClass().getName());
            sb.append(method.getName());
            for (Object obj : params) {
                if (obj != null) {
                    sb.append(obj.toString());
                }
            }
            return sb.toString();
        });
    }


    @Override
    @Bean
    public CacheManager cacheManager() {
        RedisCacheManager cacheManager = new RedisCacheManager(redisTemplate);
        cacheManager.setExpires(new HashMap<String, Long>() {{
            put(ONE_SECOND_CACHE, 1L);
            put(ONE_MINUTE_CACHE, 60L);
            put(HALF_HOUR_CACHE, 60 * 30L);
            put(ONE_DAY_CACHE, 60 * 60 * 24L);
        }});
        return cacheManager;
    }

    @Bean
    public CacheManager guavaCacheManager() {
        GuavaCacheManager cacheManager = new GuavaCacheManager();
        return cacheManager;

    }
}
