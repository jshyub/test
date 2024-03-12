package com.posco.poscoproject.service;

import com.mysql.cj.xdevapi.JsonArray;
import com.posco.poscoproject.flaskutill.FlaskRequest;
import groovy.util.logging.Slf4j;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;
import java.util.TreeMap;

@SpringBootTest
@Slf4j
class FlaskRequestTest {

    @Autowired
    FlaskRequest flaskRequest;

    WebClient client = WebClient.builder()
            .baseUrl("http://localhost:5000") // 요청 url 작성
//                .defaultCookie("cookieKey", "cookieValue")
//                .defaultHeader(MediaType.APPLICATION_JSON_VALUE)
            .build();

    // flask 서버에 요청
    public String dataRequest(int i) {

        String data = client.get().uri("chartpython"+i)
                .retrieve() // resp 의 body 데이터를 가져옴
                .bodyToMono(String.class)// bodyToMono 는 가져온 body를 해당 파라미터 객체로 변환한다.
                .block(); // 동기식으로 사용

        return data;
    }

    @Test
    @DisplayName("flask api call")
    public void flaskTEST() throws ParseException {
        String data = dataRequest(2);

        JSONParser parser = new JSONParser();
        Object obj = parser.parse(data);
        JSONObject json = (JSONObject) obj;

        System.out.println("json : " + json);
    }

    @Test
    @DisplayName("flask api call2")
    public void flaskTEST2() throws ParseException {
        String data = dataRequest(2);

        JSONParser parser = new JSONParser();
        Object obj = parser.parse(data);
        JSONObject json = (JSONObject) obj;

        System.out.println("json : " + json);
    }

    @Test
    @DisplayName("flask api call3")
    public void flaskTEST22() throws ParseException {
    }
}