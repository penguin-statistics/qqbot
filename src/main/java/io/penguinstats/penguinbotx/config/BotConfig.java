package io.penguinstats.penguinbotx.config;

import cc.moecraft.icq.PicqBotX;
import cc.moecraft.icq.PicqConfig;
import io.penguinstats.penguinbotx.service.CommandHelp;
import io.penguinstats.penguinbotx.listener.MessageListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @author ：yamika
 * @date ：Created in 2020/5/25 3:45
 * @description ：Picq cofig
 * @modified By：yamika
 * @version: 0.1
 */
@Slf4j
@Component
@PropertySource("classpath:application.properties")
public class BotConfig {

    @Value("${picq.socketport}")
    int socketPort;

    @Value("${picq.postport}")
    int postPort;

    @Value("${picq.posturl}")
    String postUrl;
    @Bean
    public PicqConfig getConfig() {
        return new PicqConfig(socketPort)
                .setApiAsync(true)
                .setDebug(true)
                .setLogPath("logs")
                .setLogFileName("Bot-Log")
                .setCommandArgsSplitRegex("|");
    }

    @Bean
    public PicqBotX getBot(@Autowired PicqConfig config){
        PicqBotX botX = new PicqBotX(config);
        botX.addAccount("penguin bot",postUrl,postPort);
        botX.enableCommandManager("bot -", "!", "/", "~","?");
        botX.getEventManager().registerListeners(new MessageListener());
        botX.getCommandManager().registerCommand(new CommandHelp());
        return botX;
    }
}
