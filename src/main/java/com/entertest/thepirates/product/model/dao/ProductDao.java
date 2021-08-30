package com.entertest.thepirates.product.model.dao;

import com.entertest.thepirates.product.model.mapper.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class ProductDao {
    @Autowired
    private ProductMapper pm;

    public void addProduct(String name,String description){
        pm.addProduct(name,description);
    }

    public int getProductNo(){
        return pm.getProductNo();
    }

    public void addDelivery(int productNo,Map<String,String> delivery){
        pm.addDelivery(productNo,delivery);
    }
    public void addOptions(int productNo, Map<String,String> options){
        pm.addOptions(productNo,options);
    }
    public List<Map<String,Object>> printList(){
        return pm.printList();
    }

    public Map<String,Object> printProduct(int productId){
        return pm.printProduct(productId);
    }
    public List<Map<String,Object>> printProductOptions(int productId){
        return pm.printProductOptions(productId);
    }
    public void deleteProduct(int productId){
        pm.deleteProduct(productId);
    }
    public Map<String,String> expiredTime(int productId){
        return pm.expiredTime(productId);
    }
}
