package io.penguinstats.penguinbotx.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @author ：yamika
 * @date ：Created in 2020/5/26 3:10
 * @description ：
 * @modified By： yamika
 * @version:
 */
@Data
public class Stage implements Serializable {

    private String stageId;
    private String stageCode;
    private String stageType;
}
