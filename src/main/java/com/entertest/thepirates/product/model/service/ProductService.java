package com.entertest.thepirates.product.model.service;

import com.entertest.thepirates.product.model.dao.ProductDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class ProductService {
    @Autowired
    private ProductDao dao;

    public void addProduct(Map p){
        dao.addProduct((String)p.get("name"), (String)p.get("description"));


       int productNo = getProductNo();
       dao.addDelivery(productNo,(Map<String,String>)p.get("delivery"));

       List<Map<String,String>> options =
               (List<Map<String, String>>) p.get("options");

        for (Map<String, String> option : options) {
            dao.addOptions(productNo, option);
        }
    }
    public int getProductNo(){
        return dao.getProductNo();
    }

    public List<Map<String,Object>> printList(){
        return dao.printList();
    }

    public Map<String,Object> printProduct(int productId){
        return dao.printProduct(productId);
    }
    public List<Map<String,Object>> printProductOptions(int productId){
        return dao.printProductOptions(productId);
    }
    public void deleteProduct(int productId){
        dao.deleteProduct(productId);
    }
    public Map<String,String> expiredTime(int productId){
        return dao.expiredTime(productId);
    }

}
