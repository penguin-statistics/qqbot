package io.penguinstats.penguinbotx.config;

import cc.moecraft.icq.PicqBotX;
import cc.moecraft.icq.PicqConfig;
import io.penguinstats.penguinbotx.Service.CommandHelp;
import io.penguinstats.penguinbotx.listener.MessageListener;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.annotation.sql.DataSourceDefinition;

/**
 * @author ：yamika
 * @date ：Created in 2020/5/25 3:45
 * @description ：Picq cofig
 * @modified By：yamika
 * @version: 0.1
 */
@Slf4j
@Component
public class BotConfig {

    @Bean
    public PicqConfig getConfig() {
        return new PicqConfig(9102)
                .setApiAsync(true)
                .setDebug(true)
                .setLogPath("logs")
                .setLogFileName("Bot-Log")
                .setCommandArgsSplitRegex("|");
    }

    @Bean
    public PicqBotX getBot(@Autowired PicqConfig config){
        PicqBotX botX = new PicqBotX(config);
        botX.addAccount("penguin bot","127.0.0.1",9101);
        botX.enableCommandManager("bot -", "!", "/", "~","?");
        botX.getEventManager().registerListeners(new MessageListener());
        botX.getCommandManager().registerCommand(new CommandHelp());
        return botX;
    }
}
