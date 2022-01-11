package finalgroup;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@SpringBootApplication
@ComponentScan(basePackages = {"finalgroup.*"})
@EntityScan(basePackages = {"finalgroup.*"})
@EnableJpaRepositories(basePackages = {"finalgroup.*"})
@EnableCaching
public class HelloWorldApplication {
    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {

        return builder
                .setConnectTimeout(Duration.ofMillis(3000))
                .setReadTimeout(Duration.ofMillis(3000))
                .build();
    }

    public static void main(String[] args) {
        SpringApplication.run(HelloWorldApplication.class, args);

    }
}
