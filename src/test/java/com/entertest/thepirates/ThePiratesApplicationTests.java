package com.entertest.thepirates;

import com.entertest.thepirates.product.CheckDate;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@AutoConfigureMockMvc
@SpringBootTest
@Slf4j
class ThePiratesApplicationTests {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void checkDate(){
        CheckDate c = new CheckDate();
        System.out.println(c.checkExpiredTime()+"현재 시간");
    }

    Map testData(){
        Map<String,Object> data= new HashMap<>();

        Map<String,String> delivery= new HashMap<>();
        List<Map<String,Object>> options= new ArrayList<>();
        Map<String,Object> option = new HashMap<>();

        data.put("name","노르웨이산 연어");
        data.put("description","노르웨이산 연어 300g, 500g, 반마리 필렛");

        delivery.put("type","fast");
        delivery.put("closing","12:00");
        data.put("delivery",delivery);

        option.put("name","생연어 몸통살 300g");
        option.put("price",10000);
        option.put("stock",99);
        options.add(option);

        data.put("options",options);

        option.put("name","생연어 몸통살 500g");
        option.put("price",17000);
        option.put("stock",99);
        options.add(option);

        data.put("options",options);

        return data;
    }
    // 상품 입력
    @Test
    void addProduct() throws Exception {
        String url="/product";
        String content = objectMapper.writeValueAsString(testData());
        log.info(content);

        mockMvc.perform(MockMvcRequestBuilders.post(url)
            .content(content)
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(mvcResult -> {
            MockHttpServletResponse res = mvcResult.getResponse();
            log.info(res.getContentAsString());
        });

        System.out.println("추가 완료");
    }

    //수령일 선택
    @Test
    void receivingDate() throws Exception {
        String url="/product/receiving/date/11";

        mockMvc.perform(MockMvcRequestBuilders.get(url)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(result -> {
                    MockHttpServletResponse response = result.getResponse();
                    log.info(response.getContentAsString());
                });
    }

    @Test
    void checkHolidays() throws Exception{
        CheckDate c = new CheckDate();
//        System.out.println(c.date()+"내일");

        for(int i=0; i<5; i++) {
            String[] dayArr = c.date(5+i).split("-");
            int month = Integer.parseInt(dayArr[1]);
            int date =  Integer.parseInt(dayArr[2]);
            System.out.println(
                    month+"월 "+
                    date+"일 "
                    +
                    c.day(Integer.parseInt(dayArr[0]),
                    month,
                    date)
            );
        }

    }

}
