package io.penguinstats.penguinbotx.service;

import cc.moecraft.icq.command.CommandProperties;
import cc.moecraft.icq.command.interfaces.GroupCommand;
import cc.moecraft.icq.event.events.message.EventGroupMessage;
import cc.moecraft.icq.user.Group;
import cc.moecraft.icq.user.GroupUser;

import java.util.ArrayList;

/**
 * @author ：yamika
 * @date ：Created in 2020/6/1 0:01
 * @description ：
 * @modified By： yamika
 * @version:
 */
public class CommandHelp implements GroupCommand {
    @Override
    public String groupMessage(EventGroupMessage eventGroupMessage, GroupUser groupUser, Group group, String s, ArrayList<String> arrayList) {
        eventGroupMessage.respond("欢迎使用Penguin Statics 群机器人\n 根据关卡名称查询物品掉落请发送：\n" +
                "查询关卡 关卡名称（复数关卡查询请以空格分隔，如查询关卡 1-7 3-2 4-3）\n" +
                "根据物品名称查询关卡掉落请发送：\n" +
                "查询物品 物品名称（复数物品查询请以空格分隔，如查询物品 研磨石 糖组 异铁组）\n" +
                "注意：1.请保证物品/关卡名称正确\n" +
                "2.目前只支持查询全局掉落，个人掉落数据暂未支持查询");
        return "欢迎使用Penguin Statics 群机器人\n 根据关卡名称查询物品掉落请发送：\n" +
                "查询关卡 关卡名称（复数关卡查询请以空格分隔，如查询关卡 1-7 3-2 4-3）\n" +
                "根据物品名称查询关卡掉落请发送：\n" +
                "查询物品 物品名称（复数物品查询请以空格分隔，如查询物品 研磨石 糖组 异铁组）\n" +
                "注意：1.请保证物品/关卡名称正确\n" +
                "2.目前只支持查询全局掉落，个人掉落数据暂未支持查询";
    }

    @Override
    public CommandProperties properties() {
        return new CommandProperties("help", "h", "帮助");
    }
}
