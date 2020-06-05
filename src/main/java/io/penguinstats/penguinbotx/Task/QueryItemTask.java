package io.penguinstats.penguinbotx.Task;

import io.penguinstats.penguinbotx.service.ItemService;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author ：yamika
 * @date ：Created in 2020/5/30 2:31
 * @description ：
 * @modified By： yamika
 * @version: 0.1
 */
@Slf4j
@Setter(onMethod = @__(@Autowired))
@Component
public class QueryItemTask {

    ItemService itemService;

    @Async("asyncExecutor")
    @Scheduled(cron = "0 1 17 * * ?")
    public void scheduledQueryItem(){
        itemService.queryItems();
        log.info("Scheduled query item at: "+new Date());
    }
}
