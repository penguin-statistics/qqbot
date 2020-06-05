package io.penguinstats.penguinbotx.Task;

import io.penguinstats.penguinbotx.service.NoticeLvzhuangService;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author ：yamika
 * @date ：Created in 2020/6/1 1:14
 * @description ：
 * @modified By： yamika
 * @version:
 */
@Slf4j
@Setter(onMethod = @__(@Autowired))
@Component
public class NoticeLvzhuangTask {

    NoticeLvzhuangService service;

    @Async("asyncExecutor")
    @Scheduled(cron = "0 59 23 * * ?")
    public void scheduledQueryItem(){
        service.noticeGirlCloth();
        log.info("Scheduled notice a bag: "+new Date());
    }
}
