package io.penguinstats.penguinbotx.Task;

import io.penguinstats.penguinbotx.Service.StageService;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Date;

/**
 * @author ：yamika
 * @date ：Created in 2020/5/26 1:51
 * @description ：scheduled stsge query
 * @modified By： yamika
 * @version:
 */
@Slf4j
@Setter(onMethod = @__(@Autowired))
@Component
public class QueryStageTask {

    StageService stageService;

    @Async("asyncExecutor")
    @Scheduled(cron = "0 0 17 * * ?")
    public void scheduledQueryStage(){
        stageService.queryStages();
        log.info("Scheduled query stages at "+new Date());
    }



}
