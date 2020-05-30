package io.penguinstats.penguinbotx.listener;

import cc.moecraft.icq.event.EventHandler;
import cc.moecraft.icq.event.IcqListener;
import cc.moecraft.icq.event.events.message.EventGroupMessage;
import cc.moecraft.icq.event.events.notice.EventNotice;
import cc.moecraft.icq.sender.message.MessageBuilder;
import io.penguinstats.penguinbotx.Service.DropService;
import io.penguinstats.penguinbotx.config.ApplicationContextAwareConfig;
import io.penguinstats.penguinbotx.constant.Constants.*;
import io.penguinstats.penguinbotx.entity.ItemDrop;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author ：yamika
 * @date ：Created in 2020/5/25 14:27
 * @description ：handle message
 * @modified By：yamika
 * @version: 0.1
 */
@Component
@Setter(onMethod = @__(@Autowired))
public class MessageListener extends IcqListener {

    DropService dropService ;

    @EventHandler
    public void refreshCache(EventNotice eventNotice){
        eventNotice.getBot().getAccountManager().refreshCache();
    }

    @EventHandler
    public void messageHandle(EventGroupMessage message){
        System.out.println("received message:"+message.getMessage());
        if(message.getMessage().matches(BotCommand.QUERY_STAGE_REGEX)){
//            TODO： query
            List<String> strs = new ArrayList<>();
            Collections.addAll(strs,message.getMessage().split(" "));
            strs.remove(0);
            List<List<ItemDrop>> results = new ArrayList<>();
            MessageBuilder builder = new MessageBuilder();
            ApplicationContext context =
                    ApplicationContextAwareConfig.getContext();
            strs.forEach(e-> results.add(context.getBean(DropService.class).queryGlobalDrobByStage(e)));
            results.forEach(e->{
                builder.add(e.get(0).getStageName()+"掉落：").newLine();
                e.forEach(t->{
                builder.add(t.getItemName()+":  ")
                        .add(t.getQuantity()+"  ")
                        .add(t.getTimes()+"  ")
                        .add(new DecimalFormat("0").format(t.getRate()*100)+
                        "%")
                        .newLine();
            });
            });
            message.respond(builder.toString());
        }
    }
}
