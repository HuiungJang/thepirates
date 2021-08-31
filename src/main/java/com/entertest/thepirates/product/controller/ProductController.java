package com.entertest.thepirates.product.controller;

import com.entertest.thepirates.product.CheckDate;
import com.entertest.thepirates.product.model.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
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
    public Map<String,Object> printListDetail(@RequestParam(value = "productid") int productId){
        System.out.println(productId);

        Map<String,Object> productInfo = service.printProduct(productId);

        List<Map<String,Object>> options = service.printProductOptions(productId);
        productInfo.put("options",options);

        return productInfo;
    }

    // 수령일 선택목록
    @GetMapping("/product/receiving/date")
    public List<Map<String,String>> receivingDate(@RequestParam(value = "productid")int productId) throws ParseException {
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
            }
        }

        // 당일 배송 아니면
        if(!type.equals("faster")){
            dateCount++;
        }

        List<Map<String,String>> result = new ArrayList<>();
        Map<String,String> answer = new HashMap<>();
        for(int i=0; i<5; i++) {
            String[] dayArr = checkDate.date(i+dateCount).split("-");

            int month = Integer.parseInt(dayArr[1]);
            int date =  Integer.parseInt(dayArr[2]);
            String dayOfWeek = checkDate.day(Integer.parseInt(dayArr[0]),month, date);

            if(dayOfWeek.equals("토요일")) {
                dateCount++;
            }else if(dayOfWeek.equals("일요일")){
                dateCount++;
            }
            answer.put("date",month+"월 "+ date+"일 "+ dayOfWeek);
            result.add(i,answer);
        }

        return result;
    }

    // 점포삭제
    @DeleteMapping("/product/{productId}")
    public void deleteProduct(@PathVariable("productId") int productId){
        service.deleteProduct(productId);
    }

}
