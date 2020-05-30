package io.penguinstats.penguinbotx.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author ：yamika
 * @date ：Created in 2020/5/29 18:47
 * @description ：
 * @modified By： yamika
 * @version: 0.1
 */
@Accessors(chain = true)
@Data
public class ItemDrop implements Serializable {
    private String stageName;
    private String stageId;
    private String itemId;
    private String itemName;
    /**
    * item drop times
    */
    private int quantity;
    /**
     * total times
     */
    private int times;
    private double rate;
}
