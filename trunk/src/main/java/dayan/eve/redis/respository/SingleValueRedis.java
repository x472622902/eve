
package dayan.eve.redis.respository;

import dayan.eve.redis.RedisKey;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * 存储单值数据
 *
 * @author xuesg
 */
@Component
public class SingleValueRedis {
    @Resource(name = "redisTemplate")
    private ValueOperations<String, String> valueOperations;

    public enum SingleKey {
        Go4Token, MoToken, WalleToken, WallePlatform, TopicAccount
    }

    /**
     * @param singleKey key
     * @param value     value
     * @param minutes   过期时间
     */
    public void put(SingleKey singleKey, String key, String value, Integer minutes) {
        if (minutes != null) {
            valueOperations.set(RedisKey.SingleValue + singleKey.name() + key, value, minutes, TimeUnit.MINUTES);
        } else {
            valueOperations.set(RedisKey.SingleValue + singleKey.name() + key, value);
        }
    }

    public String get(SingleKey singleKey, String key) throws Exception {
        return valueOperations.get(RedisKey.SingleValue.name() + singleKey.name() + key);
    }

    public void put(SingleKey singleKey, String value, Integer minutes) {
        if (minutes != null) {
            valueOperations.set(RedisKey.SingleValue + singleKey.name(), value, minutes, TimeUnit.MINUTES);
        } else {
            valueOperations.set(RedisKey.SingleValue + singleKey.name(), value);
        }
    }

    public String get(SingleKey singleKey) throws Exception {
        return valueOperations.get(RedisKey.SingleValue.name() + singleKey.name());
    }

    public void putInSeconds(SingleKey singleKey, String value, Integer seconds) {
        if (seconds != null) {
            valueOperations.set(RedisKey.SingleValue + singleKey.name(), value, seconds, TimeUnit.SECONDS);
        } else {
            valueOperations.set(RedisKey.SingleValue + singleKey.name(), value);
        }
    }
}
