package com.hyewon.springplayground.xss.controller;

import com.hyewon.springplayground.xss.dto.XssDummyDto;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/xss")
public class XssDummyController {

    @PostMapping(consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public XssDummyDto xssTest1(XssDummyDto xssDummyDto) {
        return xssDummyDto;
    }

}
