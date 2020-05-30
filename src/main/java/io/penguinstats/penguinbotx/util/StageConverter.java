package io.penguinstats.penguinbotx.util;

import io.penguinstats.penguinbotx.Exception.NotFoundException;
import io.penguinstats.penguinbotx.Service.StageService;
import io.penguinstats.penguinbotx.entity.Stage;
import lombok.Setter;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author ：yamika
 * @date ：Created in 2020/5/28 2:28
 * @description ：id to name and reverse
 * @modified By： yamika
 * @version: 0.1
 */
@Component
@Setter(onMethod = @__(@Autowired))
public class StageConverter {

StageService service;

    public Stage convert2Name(String id) throws NotFoundException{
        List<Stage> stages = service.getList();
        Stage stage = new Stage();
        stages.stream().filter(e->e.getStageId().equals(id)).forEach(e-> BeanUtils.copyProperties(e,stage));
        if(stages.size()<1){
            throw new NotFoundException("convert failed,can't find " +
                    "corresponding name,id ="+id,
                    new Throwable());
        }
        return stage;
    }

    public Stage convert2Id(String name) throws NotFoundException{
        List<Stage> stages = service.getList();
        Stage stage = new Stage();
        stages.stream().filter(e->e.getStageCode().equals(name)).forEach(e-> BeanUtils.copyProperties(e,stage));
        if(stages.size()<1){
            throw new NotFoundException("convert failed,can't find " +
                    "corresponding id,name ="+name,
                    new Throwable());
        }
        return stage;
    }


}
