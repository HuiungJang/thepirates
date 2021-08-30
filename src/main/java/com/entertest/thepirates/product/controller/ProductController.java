package com.entertest.thepirates.product.controller;

import com.entertest.thepirates.product.CheckDate;
import com.entertest.thepirates.product.model.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
public class ProductController {
    @Autowired
    private ProductService service;

    // 상품저장
    @PostMapping(value = "/product",produces = "application/json; charset=utf8")
    public void addProduct(@RequestBody Map data){
       service.addProduct(data);
    }

    //상품목록조회
    @GetMapping("/product/list")
    public List<Map<String,Object>> printList(){
        // 데이터 목록
        List<Map<String,Object>> result = service.printList();
        // 원화 콤마 표시
        DecimalFormat df = new DecimalFormat("###,###");

        for(int i =0;i<result.size(); i++){
         int price = (int) result.get(i).get("PRICE");
         String priceStr =df.format(Integer.toString(price)) +" ~ "; // 금액 콤마 표시 + ~
         // 변환한 결과 추가
         result.add(i, (Map<String, Object>) result.get(i).put("price",priceStr));
        }

        return result;
    }

    // 상품 상세 조회
    // 상품 아이디를 받아 조회함
    @GetMapping("/product/list/detail")
    public Map<String,Object> printListDetail(int productId){
        Map<String,Object> productInfo = service.printProduct(productId);

        List<Map<String,Object>> options = service.printProductOptions(productId);
        productInfo.put("options",options);

        return productInfo;
    }

    // 수령일 선택목록
    @GetMapping("/product/receiving/date")
    public List<Map<String,String>> receivingDate(@RequestBody int productId){
        int dateCount=0;
        CheckDate checkDate = new CheckDate();

        // 마감시간
        Map<String,String> requiredData = service.expiredTime(productId);
        String type= requiredData.get("TYPE");
        String expiredTime =requiredData.get("CLOSING");

        // 마감시간 지났으면
        int expiredTimeHr = Integer.parseInt(expiredTime.substring(0,2));
        // 현재시간 가져오기
        int checkDateTimeHr = Integer.parseInt(checkDate.checkExpiredTime().substring(0,2));

        if(expiredTimeHr < checkDateTimeHr){
            dateCount++;
        }
        if(expiredTimeHr == checkDateTimeHr) {
            if(Integer.parseInt(checkDate.checkExpiredTime().substring(3))>0){
                dateCount++;
            };
        }

        // 당일 배송 아니면
        if(!type.equals("faster")){
            dateCount++;
        }

        /*
            먼저해야할것
            1. 마감 시간과 현재 시간을 비교
            1-1 마감시간 안지났으면 +0, 지났으면 +1

            2. 익일 발송인지, 당일 발송인지 확인
            2-1 당일이면 +0, 익일이면 +1

            3. 주말껴있는지 확인
            3-1 안껴있으면 +0
            3-2 하루 끼어있으면 +1, 이틀끼어있으면 +2

            4. 공휴일 껴있는지 확인
            4-1 안껴있으면 +0
            4-2 끼어있는 일수대로 +

            마지막 +1일
            + 된 일수 이후 날짜,요일 5개 출력
         */
        List<Map<String,String>> result = new ArrayList<>();

        return result;
    }

    // 점포삭제
    @DeleteMapping("/product/{productId}")
    public void deleteProduct(@PathVariable("productId") int productId){
        service.deleteProduct(productId);
    }

}
