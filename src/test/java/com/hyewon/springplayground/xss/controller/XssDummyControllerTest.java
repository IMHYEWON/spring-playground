package com.hyewon.springplayground.xss.controller;

import com.hyewon.springplayground.xss.dto.XssDummyDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@AutoConfigureMockMvc
class XssDummyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Nested
    @DisplayName("XSS 필터링 되기 전 테스트")
    class XssNotFiltered {
        @Test
        @DisplayName("[성공] 필터링이 되지 않고 요청값 그대로 리턴되어야 한다.")
        void xssTest1() throws Exception {
            String data = "<script>alert('XSS');</script>";
            mockMvc.perform(MockMvcRequestBuilders.post("/xss")
                            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                            .param("data", data))
                    .andDo(print())
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(MockMvcResultMatchers.content().json("{\"data\":\"<script>alert('XSS');</script>\"}"));
        }

        @Test
        @DisplayName("[실패] 필터링이 되면 안됨.")
        void xssTest2() throws Exception {
            String data = "<script>alert('XSS');</script>";
            MvcResult response = mockMvc.perform(MockMvcRequestBuilders.post("/xss")
                            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                            .param("data", data))
                    .andDo(print())
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andReturn();

            String content = response.getResponse().getContentAsString();
            assert !content.contains("&lt;script&gt;alert('XSS');&lt;/script&gt;");
        }

    }
}