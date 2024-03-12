package com.posco.poscoproject.controller;

import com.posco.poscoproject.flaskutill.FlaskRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class FlaskController {

    private final FlaskRequest flaskRequest;

    // Flask api 요청
    @GetMapping("chartpython")
    public @ResponseBody String pythonChart(){

        return null;
    }
}
