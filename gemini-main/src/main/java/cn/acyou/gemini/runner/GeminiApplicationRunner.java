package cn.acyou.gemini.runner;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;


/**
 * 这里通过设定value的值来指定执行顺序
 * @author youfang
 * @version [1.0.0, 2018-8-28 下午 11:02]
 **/

@Component
public class GeminiApplicationRunner implements ApplicationRunner {

    @Override
    public void run(ApplicationArguments var1) {
        //log.info("MyApplicationRunner  was started!");
    }


}
