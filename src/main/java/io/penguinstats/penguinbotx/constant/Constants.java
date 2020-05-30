package io.penguinstats.penguinbotx.constant;

/**
 * @author ：yamika
 * @date ：Created in 2020/5/26 2:09
 * @description ：global constants
 * @modified By： yamika
 * @version: 0.1
 */
public class Constants {

     public static class PenguiUrl{
        public static final String PENGUIN_STAGE_API ="https://penguin-stats.io/PenguinStats/api/v2/stages";
        public static final String PENGUIN_ITEM_API = "https://penguin-stats" +
                ".io/PenguinStats/api/v2/items";
        public static final String PENGUIN_ADVANCED_QUERY = "https://penguin-stats.io/PenguinStats/api/v2/result/advanced";
        public static final String PENGUIN_LOGIN_API = "https://penguin-stats" +
                ".io/PenguinStats/api/v2/users";
         public static final String PENGUIN_FORMULA_API = "https://penguin" +
                 "-stats.io/PenguinStats/api/v2/formula";
         public static final String PENGUIN_MATRIX_API = "https://penguin" +
                 "-stats.io/PenguinStats/api/v2/result/matrix";
     }

     public static class BotCommand{
//         修改正则
        public static final String QUERY_STAGE_REGEX = "^查询关卡.*";
        public static final String QUERY_ITEM_REGEX = "^查询物品.*";
    }
}
