package io.penguinstats.penguinbotx.Service;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.penguinstats.penguinbotx.Exception.NotFoundException;
import io.penguinstats.penguinbotx.constant.Constants;
import io.penguinstats.penguinbotx.entity.ItemDrop;
import io.penguinstats.penguinbotx.entity.Stage;
import io.penguinstats.penguinbotx.entity.query.AdvancedQuery;
import io.penguinstats.penguinbotx.util.ItemConverter;
import io.penguinstats.penguinbotx.util.StageConverter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author ：yamika
 * @date ：Created in 2020/5/29 1:22
 * @description ：query drops
 * @modified By： yamika
 * @version: 0.1
 */
@Slf4j
@Service
@Setter(onMethod = @__(@Autowired))
public class DropService {

    StageConverter stageConverter;
    ItemConverter  itemConverter;

    public List<ItemDrop> queryGlobalDrobByStage(String stageName) {
        Stage stage = null;
        try {
             stage = stageConverter.convert2Id(stageName);
        } catch (NotFoundException e) {
            log.error(e.getMessage());
            e.printStackTrace();
        }
        HttpHeaders headers = new HttpHeaders();
        headers.add("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36");
        MediaType type = MediaType.parseMediaType("application/json;" +
                "charset=UTF-8");
        headers.setContentType(type);
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
        Map<String, List<AdvancedQuery>> queryMap = new HashMap<>(1);
        List<AdvancedQuery> list = new ArrayList<>();
        list.add(new AdvancedQuery(1556668800000L, "CN"
                , Objects.requireNonNull(stage).getStageId()));
        queryMap.put("queries", list);
        String json =
                null;
        try {
            json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(queryMap);
        } catch (JsonProcessingException e) {
            log.error("json generate failed:" + e.getMessage());
            e.printStackTrace();
        }
        log.info("query sql is "+json);
        HttpEntity<String> httpEntity =
                new HttpEntity<>(json, headers);
        RestTemplate template = new RestTemplate();
        ResponseEntity<String> responseEntity =
                template.exchange(Constants.PenguiUrl.PENGUIN_ADVANCED_QUERY,
                        HttpMethod.POST, httpEntity, String.class);
        List<ItemDrop> drops = new ArrayList<>();
        String[] strs = Objects.requireNonNull(responseEntity.getBody()).split("\\{");
        List<String> dropList =
                Arrays.stream(strs).filter(e -> e.contains("itemId")).collect(Collectors.toList());
        for (String s : dropList) {
            ItemDrop drop = new ItemDrop();
            int id = s.indexOf("itemId");
            int qua = s.indexOf("quantity");
            int tines = s.indexOf("times");
            int timesend = s.indexOf("start");
            drop.setItemId(s.substring(id + 9, qua - 3));
            int q = Integer.parseInt(s.substring(qua + 10, tines - 2));
            drop.setQuantity(q);
            int t = Integer.parseInt(s.substring(tines + 7, timesend - 2));
            drop.setTimes(t);
            drop.setRate((float)q / t);
            drop.setStageId(stage.getStageId());
            drop.setStageName(stageName);
            drop.setItemName(itemConverter.convert(drop.getItemId()).getItemName());
            drops.add(drop);
        }
        return drops;
    }
}
