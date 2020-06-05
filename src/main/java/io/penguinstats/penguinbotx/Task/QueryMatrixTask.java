package io.penguinstats.penguinbotx.Task;

import io.penguinstats.penguinbotx.service.DropService;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author ：yamika
 * @date ：Created in 2020/6/1 1:12
 * @description ：
 * @modified By： yamika
 * @version:
 */
@Slf4j
@Setter(onMethod = @__(@Autowired))
@Component
public class QueryMatrixTask {

    DropService dropService;

    @Async("asyncExecutor")
    @Scheduled(cron = "0 5 17 * * ?")
    public void scheduledQueryItem(){
        dropService.queryMatrix();
        log.info("Scheduled query drop matrix at: "+new Date());
    }

}
