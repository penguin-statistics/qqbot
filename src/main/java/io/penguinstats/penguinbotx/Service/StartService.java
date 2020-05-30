package io.penguinstats.penguinbotx.Service;

import cc.moecraft.icq.PicqBotX;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author ：yamika
 * @date ：Created in 2020/5/30 3:51
 * @description ：
 * @modified By： yamika
 * @version:
 */
@Service
@Slf4j
@Setter(onMethod = @__(@Autowired))
public class StartService implements ApplicationRunner {
    PicqBotX botX;
    StageService stageService;
    ItemService itemService;
    DropService dropService;

    @Override
    public void run(ApplicationArguments args) {
        log.info("application init at: "+new Date());
        log.info("Query stages when init at"+new Date());
        stageService.queryStages();
        log.info("Query items  when init at: "+new Date());
        itemService.queryItems();
        log.info("query drop matrix when init at: "+new Date());
        dropService.queryMatrix();
        log.info("start bot: "+new Date());
        botX.startBot();
    }
}
