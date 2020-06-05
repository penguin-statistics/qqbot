package io.penguinstats.penguinbotx;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.penguinstats.penguinbotx.service.DropService;
import io.penguinstats.penguinbotx.constant.Constants;
import io.penguinstats.penguinbotx.entity.ItemDrop;
import io.penguinstats.penguinbotx.entity.Stage;
import io.penguinstats.penguinbotx.entity.query.AdvancedQuery;
import io.penguinstats.penguinbotx.entity.response.MatrixResponse;
import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;

@SpringBootTest
class PenguinbotxApplicationTests {

    @Autowired
    DropService dropService;


    @Test
    public void myTest(){
        String str = "{\"advanced_results\":[{\"matrix" +
                "\":[{\"stageId\":\"main_01-07\",\"itemId\":\"randomMaterial_3\",\"quantity\":5397,\"times\":48838,\"start\":1589529600000,\"end\":1590696000000},{\"stageId\":\"main_01-07\",\"itemId\":\"randomMaterial_1\",\"quantity\":3699,\"times\":31437,\"start\":1577174400000,\"end\":1578340800000},{\"stageId\":\"main_01-07\",\"itemId\":\"randomMaterial_2\",\"quantity\":2060,\"times\":17393,\"start\":1581105600000,\"end\":1582315200000},{\"stageId\":\"main_01-07\",\"itemId\":\"2001\",\"quantity\":221517,\"times\":180802,\"start\":1556676000000,\"end\":1590696000000},{\"stageId\":\"main_01-07\",\"itemId\":\"furni\",\"quantity\":1376,\"times\":180802,\"start\":1556676000000,\"end\":1590696000000},{\"stageId\":\"main_01-07\",\"itemId\":\"30051\",\"quantity\":8429,\"times\":180802,\"start\":1556676000000,\"end\":1590696000000},{\"stageId\":\"main_01-07\",\"itemId\":\"30061\",\"quantity\":6135,\"times\":180802,\"start\":1556676000000,\"end\":1590696000000},{\"stageId\":\"main_01-07\",\"itemId\":\"30012\",\"quantity\":224960,\"times\":180802,\"start\":1556676000000,\"end\":1590696000000},{\"stageId\":\"main_01-07\",\"itemId\":\"30031\",\"quantity\":10668,\"times\":180802,\"start\":1556676000000,\"end\":1590696000000},{\"stageId\":\"main_01-07\",\"itemId\":\"30041\",\"quantity\":8291,\"times\":180802,\"start\":1556676000000,\"end\":1590696000000},{\"stageId\":\"main_01-07\",\"itemId\":\"30011\",\"quantity\":21510,\"times\":180802,\"start\":1556676000000,\"end\":1590696000000},{\"stageId\":\"main_01-07\",\"itemId\":\"30021\",\"quantity\":10213,\"times\":180802,\"start\":1556676000000,\"end\":1590696000000},{\"stageId\":\"main_01-07\",\"itemId\":\"3003\",\"quantity\":16215,\"times\":180802,\"start\":1556676000000,\"end\":1590696000000},{\"stageId\":\"main_01-07\",\"itemId\":\"ap_supply_lt_010\",\"quantity\":7280,\"times\":80275,\"start\":1577174400000,\"end\":1590696000000}]}]}";
        String[] strs = str.split("\\{");
        List<String> list = Arrays.stream(strs).filter(e->e.contains("itemId")).collect(Collectors.toList());
        for (String s : list) {
            int id=s.indexOf("itemId");
            int qua=s.indexOf("quantity");
            int tines=s.indexOf("times");
            int timesend=s.indexOf("start");
            System.out.println("ids:"+s.substring(id+9,qua-3));
            System.out.println("quantity:"+Integer.parseInt(s.substring(qua+10,tines-2)));
            System.out.println("times:"+Integer.parseInt(s.substring(tines+7,timesend-2)));
            System.out.println();
        }
    }

    @Test
    void responseTest(){
        HttpHeaders headers = new HttpHeaders();
        headers.add("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36");
        HttpEntity<String> httpEntity = new HttpEntity<>(headers);
        RestTemplate template = new RestTemplate();
        Map<String,String> parameter = new HashMap<>();
//        TODO : support foreign server
        parameter.put("server","CN");
        ParameterizedTypeReference<List<Object>> reference =
                new ParameterizedTypeReference<List<Object>>() {
        };
        ResponseEntity<List< Object>> response =
                template.exchange(Constants.PenguiUrl.PENGUIN_STAGE_API,
                        HttpMethod.GET,httpEntity,reference,parameter);
        Objects.requireNonNull(response.getBody()).forEach(e->{
            String[] stagestrs = e.toString().split(",");
            Stage stage = new Stage();
            stage.setStageType(stagestrs[0].substring(10));
            stage.setStageId(stagestrs[1].substring(8));
            stage.setStageCode(stagestrs[3].substring(5));
            System.out.println(stage);
        });
    }


    @Test
    public void AdvanceQueryTest() throws JSONException, JsonProcessingException {
        HttpHeaders headers = new HttpHeaders();
        headers.add("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36");
        MediaType type = MediaType.parseMediaType("application/json;" +
                "charset=UTF-8");
        headers.setContentType(type);
/*        MultiValueMap<String,Object> map = new LinkedMultiValueMap<>();
        map.add("end",System.currentTimeMillis());
        System.out.println(System.currentTimeMillis());
        map.add("start",1558989300000L);
        map.add("isPersonal",false);
        map.add("server","CN");
        map.add("itemIds",new ArrayList<String>());
        map.add("stageId","main_04_06");*/
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
        Map<String,List<AdvancedQuery>> queryMap = new HashMap<>();
        List<AdvancedQuery> list = new ArrayList<>();
        list.add(new AdvancedQuery(1556668800000L,"CN"
                ,"main_01-07"));
        queryMap.put("queries",list);
        String json=
                mapper.writerWithDefaultPrettyPrinter().writeValueAsString(queryMap);
        System.out.println(json);
        HttpEntity<String> httpEntity =
                new HttpEntity<>(json,headers);
        RestTemplate template = new RestTemplate();
        ResponseEntity<String> responseEntity =
                template.exchange(Constants.PenguiUrl.PENGUIN_ADVANCED_QUERY,
                        HttpMethod.POST,httpEntity,String.class);
        System.out.println(responseEntity.getBody());

    }

    @Test
    public void loginAndGetTest() throws JsonProcessingException {
        HttpHeaders headers = new HttpHeaders();
        headers.add("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36");
        MediaType type = MediaType.parseMediaType("application/json;" +
                "charset=UTF-8");
        headers.setContentType(type);
        List<String> cookies = new ArrayList<>();
        cookies.add("userID=19961022");
        headers.put(HttpHeaders.COOKIE, cookies);
        RestTemplate template = new RestTemplate();

        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
        Map<String,List<AdvancedQuery>> queryMap = new HashMap<>();
        List<AdvancedQuery> list = new ArrayList<>();
        AdvancedQuery query = new AdvancedQuery(1556668800000L,"CN"
                ,"main_01-07");
        query.setPersonal(true);
        list.add(query);
        queryMap.put("queries",list);
        String json=
                mapper.writerWithDefaultPrettyPrinter().writeValueAsString(queryMap);
        System.out.println(json);
        HttpEntity<String> httpEntity1 =
                new HttpEntity<>(json,headers);
        ResponseEntity<String> responseEntity1 =
                template.exchange(Constants.PenguiUrl.PENGUIN_ADVANCED_QUERY,
                        HttpMethod.POST,httpEntity1,String.class);
        System.out.println(responseEntity1.getBody());
    }

    @Test
    public void parseFormulaTest(){
        HttpHeaders headers = new HttpHeaders();
        headers.add("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36");
        HttpEntity<String> httpEntity = new HttpEntity<>(headers);
        RestTemplate template = new RestTemplate();
        Map<String,String> parameter = new HashMap<>();
//        TODO : support foreign server
//        parameter.put("server","CN");
        ParameterizedTypeReference<List<Object>> reference =
                new ParameterizedTypeReference<List<Object>>() {
                };
        ResponseEntity<List< Object>> response =
                template.exchange(Constants.PenguiUrl.PENGUIN_FORMULA_API,
                        HttpMethod.GET,httpEntity,reference);

        String testStr = "{id=30024, name=糖聚块, goldCost=300, " +
                "costs=[{id=30023, name=糖组, rarity=2, count=2}, {id=30043, name=异铁组, rarity=2, count=1}, {id=30083, name=轻锰矿, rarity=2, count=1}], extraOutcome=[{id=30013, name=固源岩组, rarity=2, count=1, weight=60}, {id=30023, name=糖组, rarity=2, count=1, weight=50}, {id=30033, name=聚酸酯组, rarity=2, count=1, weight=50}, {id=30043, name=异铁组, rarity=2, count=1, weight=40}, {id=30053, name=酮凝集组, rarity=2, count=1, weight=40}, {id=30063, name=全新装置, rarity=2, count=1, weight=30}, {id=30073, name=扭转醇, rarity=2, count=1, weight=45}, {id=30083, name=轻锰矿, rarity=2, count=1, weight=40}, {id=30093, name=研磨石, rarity=2, count=1, weight=36}, {id=30103, name=RMA70-12, rarity=2, count=1, weight=30}, {count=1, id=31013, name=凝胶, rarity=2, weight=36}, {count=1, id=31023, name=炽合金, rarity=2, weight=40}], totalWeight=497}";
        Objects.requireNonNull(response.getBody()).forEach(s->{
            String itemStr = s.toString();
            String itemId = "";
            String itemName = "";
            int idBegin = itemStr.indexOf("id");
            if (idBegin>3){
                 idBegin = itemStr.lastIndexOf("id")+3;
                itemId = itemStr.substring(idBegin,idBegin+5);
                int nameBegin = itemStr.lastIndexOf("name")+5;
                int nameEnd = itemStr.lastIndexOf("totalWeight")-2;
                itemName = itemStr.substring(nameBegin,nameEnd);
            }else{
                 idBegin = itemStr.indexOf("id")+3;
                itemId = itemStr.substring(idBegin,idBegin+5);
                int nameBegin = itemStr.indexOf("name")+5;
                int nameEnd = itemStr.indexOf("goldCost")-2;
                itemName = itemStr.substring(nameBegin,nameEnd);
                if(itemStr.contains("rarity=0")){
                    int end = itemStr.indexOf("rarity" +
                            "=0");
                    int start  = itemStr.indexOf("costs");
                    int r0id = itemStr.indexOf("id",start);
                    int r0name = itemStr.indexOf("name",start);
                    System.out.println("r0:  id:"+itemStr.substring(r0id+3,
                            r0name-1)+"  name:"+itemStr.substring(r0name+5,
                            end-2));
                }
            }
            System.out.println("id:"+itemId+"  name:"+itemName);
        });
    }

    @Test
    public void queryItems(){
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
            int name_end = strs.indexOf("name_i");
            String id = strs.substring(idBegin+7,nameBegin-2);
            String name = strs.substring(nameBegin+5,name_end-2);
            System.out.println("id:"+id+"name:"+name);
        });
    }

    @Test
    public void matrixTest(){
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
            System.out.println("itemid:"+itemId+"  stageid:"+stageId+"  times" +
                    ":"+times+"  qua:"+quantity);
        });
    }

    @Test
    public void dropTest(){
        dropService.queryGlobalDrobByStage("1-7").forEach(System.out::println);
    }

}
