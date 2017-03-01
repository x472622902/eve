package dayan.eve.redis;

import dayan.eve.model.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import redis.embedded.RedisServer;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;

/**
 * 进行测试时，关闭防火墙
 * redis 启动不了时 控制台杀死java 和redis-server 再启动程序
 *
 * @author xuesg
 */
@Configuration
public class EmbeddedRedis {
    private RedisServer redisServer;


    @Autowired
    private Environment env;

    @PostConstruct
    public void startRedis() throws IOException {
        Collection<String> activeProfiles = Arrays.asList(env.getActiveProfiles());
        if (activeProfiles.contains(Constants.SPRING_PROFILE_NO_EMBEDDED_REDIS)
                || activeProfiles.contains(Constants.SPRING_PROFILE_PRODUCTION)
                || activeProfiles.contains(Constants.SPRING_PROFILE_ONLINE_TEST)) {
            return;
        }
        redisServer = new RedisServer(6379);
        if (redisServer.isActive()) {
            return;
        }
        redisServer.start();
    }

    @PreDestroy
    public void stopRedis() {
        Collection<String> activeProfiles = Arrays.asList(env.getActiveProfiles());
        if (activeProfiles.contains(Constants.SPRING_PROFILE_NO_EMBEDDED_REDIS)
                || activeProfiles.contains(Constants.SPRING_PROFILE_PRODUCTION)) {
            return;
        }
        redisServer.stop();
    }
}
