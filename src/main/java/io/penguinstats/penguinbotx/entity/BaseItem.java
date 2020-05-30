package io.penguinstats.penguinbotx.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * @author ：yamika
 * @date ：Created in 2020/5/30 2:00
 * @description ：
 * @modified By： yamika
 * @version: 0.1
 */
@AllArgsConstructor
@Data
public class BaseItem implements Serializable {
    private String itemId;
    private String itemName;
}
