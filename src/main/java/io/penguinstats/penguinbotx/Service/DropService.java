package io.penguinstats.penguinbotx.Service;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.penguinstats.penguinbotx.Exception.NotFoundException;
import io.penguinstats.penguinbotx.constant.Constants;
import io.penguinstats.penguinbotx.entity.ItemDrop;
import io.penguinstats.penguinbotx.entity.Stage;
import io.penguinstats.penguinbotx.entity.query.AdvancedQuery;
import io.penguinstats.penguinbotx.entity.response.MatrixResponse;
import io.penguinstats.penguinbotx.util.ItemConverter;
import io.penguinstats.penguinbotx.util.StageConverter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.ApplicationContext;
import org.springframework.core.ParameterizedTypeReference;
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
    ApplicationContext context;

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

    public List<ItemDrop> queryGlobalByItem(String itemName){
        List<ItemDrop> matrix = getProxy().getMatrix();
        return matrix.stream().filter(e->e.getItemName().equals(itemName)).collect(Collectors.toList());
    }

    @CachePut(cacheNames = "matrixCache",key = "'matrix'")
    public List<ItemDrop> queryMatrix(){
        log.info("query matrix at: "+new Date());
        List<ItemDrop> matrix = new ArrayList<>();
        HttpHeaders headers = new HttpHeaders();
        headers.add("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36");
        HttpEntity<String> httpEntity = new HttpEntity<>(headers);
        RestTemplate template = new RestTemplate();
        Map<String,String> parameter = new HashMap<>(1);
//        TODO : support foreign server
        parameter.put("is_personal","false");
        parameter.put("server","CN");
        parameter.put("show_closed_zones","false");
        ParameterizedTypeReference<MatrixResponse> reference =
                new ParameterizedTypeReference<MatrixResponse>() {
                };
        ResponseEntity<MatrixResponse> response =
                template.exchange(Constants.PenguiUrl.PENGUIN_MATRIX_API,
                        HttpMethod.GET,httpEntity,reference,parameter);
        Objects.requireNonNull(response.getBody()).getMatrix().forEach(e->{
            ItemDrop drop = new ItemDrop();
            String dropStr = e.toString();
            int stageIndex = dropStr.indexOf("stageId");
            int itemIndex = dropStr.indexOf("itemId");
            int quaIndex = dropStr.indexOf("quantity");
            int timeIndex = dropStr.indexOf("times");
            int end = dropStr.indexOf("start");
            String stageId = dropStr.substring(stageIndex+8,itemIndex-2);
            String itemId = dropStr.substring(itemIndex+7,quaIndex-2);
            int quantity =Integer.parseInt(dropStr.substring(quaIndex+9,
                    timeIndex-2));
            int times = Integer.parseInt(dropStr.substring(timeIndex+6,end-2));
            String itemName = itemConverter.convert(itemId).getItemName();
            String stageName = stageConverter.convert2Name(stageId).getStageCode();
            drop.setStageName(stageName).setItemName(itemName).setStageId(stageId).setItemId(itemId).setTimes(times).setQuantity(quantity).setRate((float)quantity/times);
            matrix.add(drop);
        });
        return matrix;
    }

    @Cacheable(cacheNames = "matrixCache",key = "'matrix'")
    public List<ItemDrop> getMatrix(){
        return getProxy().queryMatrix();
    }

    public DropService getProxy(){
        return context.getBean(DropService.class);
    }
}
