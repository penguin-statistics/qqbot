package io.penguinstats.penguinbotx.entity.query;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ：yamika
 * @date ：Created in 2020/5/29 2:49
 * @description ：
 * @modified By： yamika
 * @version:
 */
@Data
public class AdvancedQuery implements Serializable {
    @NonNull
    Long start;
    Long end;
    @NonNull
    String server;
    String stageId;
    Integer interval;
    List<String> itemIds;
    boolean isPersonal;

    public AdvancedQuery(long start,String server,String stageId){
        this.start=start;
        this.server=server;
        this.stageId=stageId;
        this.isPersonal=false;
        this.itemIds=new ArrayList<>();
    }
}
