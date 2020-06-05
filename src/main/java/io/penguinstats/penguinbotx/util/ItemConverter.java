package io.penguinstats.penguinbotx.util;

import io.penguinstats.penguinbotx.service.ItemService;
import io.penguinstats.penguinbotx.entity.BaseItem;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

/**
 * @author ：yamika
 * @date ：Created in 2020/5/30 2:34
 * @description ：
 * @modified By： yamika
 * @version:
 */
@Setter(onMethod = @__(@Autowired))
@Component
public class ItemConverter {

    ItemService itemService;

    public BaseItem convert(String id){
        return itemService.getList().stream().filter(e->e.getItemId().equals(id)).collect(Collectors.toList()).get(0);
    }

}
