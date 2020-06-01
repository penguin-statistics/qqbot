package io.penguinstats.penguinbotx.Service;

import cc.moecraft.icq.PicqBotX;
import cc.moecraft.icq.sender.HttpApiNode;
import cc.moecraft.icq.sender.IcqHttpApi;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author ：yamika
 * @date ：Created in 2020/6/1 1:15
 * @description ：
 * @modified By： yamika
 * @version:
 */
@Slf4j
@Service
@Setter(onMethod = @__(@Autowired))
public class NoticeLvzhuangService {
    PicqBotX botX;

    public void noticeGirlCloth(){
        IcqHttpApi httpApi =
                botX.getAccountManager().getNonAccountSpecifiedApi();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");
        LocalDateTime date = LocalDateTime.now();
        String msg = "现在是"+dtf.format(date)+"今天阿包女装了吗？";
        httpApi.sendGroupMsg(74709962L,msg);
        httpApi.sendGroupMsg(686716604L,msg);
        httpApi.sendGroupMsg(1021857461L,msg);
    }
}
