package com.posco.poscoproject.flaskutill;

import com.mysql.cj.xdevapi.JsonArray;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;


@Component
@RequiredArgsConstructor
public class FlaskRequest {

    // api 요청시 사용하는 클래스
    WebClient client = WebClient.builder()
            .baseUrl("http://localhost:5000") // 요청 url 작성
//                .defaultCookie("cookieKey", "cookieValue")
//                .defaultHeader(MediaType.APPLICATION_JSON_VALUE)
            .build();

    // flask 서버에 요청
    public JSONObject topTenRequest(){

        String data = client.get().uri("topten")
                .retrieve() // resp 의 body 데이터를 가져옴
                .bodyToMono(String.class)// bodyToMono 는 가져온 body를 해당 파라미터 객체로 변환한다.
                .block(); // 동기식으로 사용

        JSONParser parser = new JSONParser();
        JSONObject json = null;

        try {
            json = (JSONObject) parser.parse(data);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        return json;

    }
}
