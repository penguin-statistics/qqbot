package io.penguinstats.penguinbotx.Task;

import cc.moecraft.icq.PicqBotX;
import io.penguinstats.penguinbotx.Service.ItemService;
import io.penguinstats.penguinbotx.Service.StageService;
import io.penguinstats.penguinbotx.config.BotConfig;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.Date;

/**
 * @author ：yamika
 * @date ：Created in 2020/5/30 2:29
 * @description ：
 * @modified By： yamika
 * @version: 0.1
 */
@Slf4j
@Setter(onMethod = @__(@Autowired))
public class InitTask {

    PicqBotX botX;
    StageService stageService;
    ItemService itemService;

    @PostConstruct
    public void init(){
        System.out.println("-------------init-------------------");
        log.info("Query stages when init at"+new Date());
        stageService.queryStages();
        log.info("Query item formula when init at"+new Date());
        itemService.queryItems();
        log.info("start bot:"+new Date());
        botX.startBot();
    }
}
