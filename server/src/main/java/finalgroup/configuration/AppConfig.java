package finalgroup.configuration;

import finalgroup.entity.Wallet;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;


@Configuration
public class AppConfig {

    //Creating Connection with Redis
    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        return new LettuceConnectionFactory();
    }

    //Creating RedisTemplate for Entity wallet
    @Bean
    public RedisTemplate<String, Wallet> redisTemplate(){
        RedisTemplate<String, Wallet> walletTemplate = new RedisTemplate<>();
        walletTemplate.setConnectionFactory(redisConnectionFactory());
        return walletTemplate;
    }
}
