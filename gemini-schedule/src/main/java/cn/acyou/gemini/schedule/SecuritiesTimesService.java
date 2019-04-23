package cn.acyou.gemini.schedule;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author youfang
 * @version [1.0.0, 2019-04-22 下午 03:32]
 **/
@Slf4j
@Component
public class SecuritiesTimesService {

    public SecuritiesTimesService() {
        log.info("SecuritiesTimesService 初始化");
    }

    @Scheduled(cron = "0 */30 * * * ?")
    public void hushenA(){
        log.info("—————— 每30分钟执行一次 ——————");
    }
}
