package io.penguinstats.penguinbotx.Service;

import com.google.gson.JsonObject;
import io.penguinstats.penguinbotx.constant.Constants;
import io.penguinstats.penguinbotx.entity.Stage;
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

import io.penguinstats.penguinbotx.constant.Constants.PenguiUrl;
/**
 * @author ：yamika
 * @date ：Created in 2020/5/26 1:53
 * @description ：
 * @modified By： yamika
 * @version:
 */
@Slf4j
@Service
public class StageService {

    @Autowired
    ApplicationContext context;

    @CachePut(cacheNames = "stageCache",key = "'stages'")
    public List<Stage> queryStages(){
        log.info("query stages");
        List<Stage> stages = new ArrayList<>();
        HttpHeaders headers = new HttpHeaders();
        headers.add("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36");
        HttpEntity<String> httpEntity = new HttpEntity<>(headers);
        RestTemplate template = new RestTemplate();
        Map<String,String> parameter = new HashMap<>(1);
//        TODO : support foreign server
        parameter.put("server","CN");
        ParameterizedTypeReference<List<Object>> reference =
                new ParameterizedTypeReference<List<Object>>() {
                };
        ResponseEntity<List<Object>> response =
                template.exchange(Constants.PenguiUrl.PENGUIN_STAGE_API,
                        HttpMethod.GET,httpEntity,reference,parameter);
        Objects.requireNonNull(response.getBody()).forEach(e->{
            String[] stagestrs = e.toString().split(",");
            Stage stage = new Stage();
            stage.setStageType(stagestrs[0].substring(11));
            stage.setStageId(stagestrs[1].substring(9));
            stage.setStageCode(stagestrs[3].substring(6));
            stages.add(stage);
        });
        return stages;
    }

    @Cacheable(cacheNames = "stageCache",key = "'stages'")
    public List<Stage> getList(){
        return getProxy().queryStages();
    }

    public StageService getProxy(){
        return context.getBean(StageService.class);
    }
}
