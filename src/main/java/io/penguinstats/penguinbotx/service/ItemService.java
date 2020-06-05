package io.penguinstats.penguinbotx.service;

import io.penguinstats.penguinbotx.constant.Constants;
import io.penguinstats.penguinbotx.entity.BaseItem;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.ApplicationContext;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

/**
 * @author ：yamika
 * @date ：Created in 2020/5/30 2:19
 * @description ：
 * @modified By： yamika
 * @version:
 */
@Slf4j
@Service
public class ItemService {
    @Autowired
    ApplicationContext context;

    @CachePut(cacheNames = "itemCache",key = "'items'")
    public List<BaseItem> queryItems(){
        List<BaseItem> items = new ArrayList<>();
        HttpHeaders headers = new HttpHeaders();
        headers.add("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36");
        HttpEntity<String> httpEntity = new HttpEntity<>(headers);
        RestTemplate template = new RestTemplate();
        ParameterizedTypeReference<List<Object>> reference =
                new ParameterizedTypeReference<List<Object>>() {
                };
        ResponseEntity<List< Object>> response =
                template.exchange(Constants.PenguiUrl.PENGUIN_ITEM_API,
                        HttpMethod.GET,httpEntity,reference);
        Objects.requireNonNull(response.getBody()).forEach(e->{

            String strs = e.toString();
            int idBegin = strs.indexOf("itemId");
            int nameBegin = strs.indexOf("name");
            int nameEnd = strs.indexOf("name_i");
            String id = strs.substring(idBegin+7,nameBegin-2);
            String name = strs.substring(nameBegin+5,nameEnd-2);
            BaseItem baseItem = new BaseItem(id,name);
            items.add(baseItem);
        });
        return items;
    }

    @Cacheable(cacheNames = "itemCache",key = "'items'")
    public List<BaseItem> getList(){
        return getProxy().queryItems();
    }

    public ItemService getProxy(){
        return context.getBean(ItemService.class);
    }
}
