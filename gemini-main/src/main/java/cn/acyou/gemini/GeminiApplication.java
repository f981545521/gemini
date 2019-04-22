package cn.acyou.gemini;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author youfang
 * @version [1.0.0, 2019-04-12 上午 09:31]
 **/
@SpringBootApplication
@EnableScheduling
public class GeminiApplication {

    public static void main(String[] args) {
        SpringApplication.run(GeminiApplication.class, args);
    }
    
}
