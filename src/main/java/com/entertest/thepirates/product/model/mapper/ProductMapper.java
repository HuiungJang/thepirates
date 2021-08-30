package com.entertest.thepirates.product.model.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;


@Mapper
public interface ProductMapper {
    // 상품저장
    @Insert("insert into product values(product_seq.nextval,#{name},#{description},sysdate)")
    void addProduct(String name, String description);

    // 상품 번호 가져오는 쿼리
    @Select("select *" +
            "from (" +
            "         select product_no" +
            "         from PRODUCT" +
            "         order by product_no desc" +
            "         )" +
            "where rownum = 1")
    int getProductNo();

    // 배송정보 저장
    @Insert("insert into delivery values(#{productNo},#{delivery.type},#{delivery.closing},0,'0')")
    void addDelivery(int productNo, Map<String,String> delivery);

    // 옵션정보 저장
    @Insert("insert into options values(#{productNo},#{options.name},#{options.price},#{options.stock})")
    void addOptions(int productNo, Map<String,String> options);

    // 목록 출력
    @Select("select p.name,p.description, min(o.price) as price" +
            " from PRODUCT p join OPTIONS O on p.product_no = O.PRODUCT_NO" +
            " group by o.price,p.date" +
            " order by p.DATE desc")
    List<Map<String,Object>> printList();

    // 상품 상세 조회
    @Select("select p.name,p.description,d.type" +
            " from PRODUCT p" +
            "    join Delivery d on p.product_no = d.product_no" +
            " where p.product_no = #{productId}")
    Map<String,Object> printProduct(int productId);

    @Select("select name,price" +
            " from Options" +
            " where product_no=#{productNo}")
    List<Map<String,Object>> printProductOptions(int productId);

    // 수령일 선택목록
    // 마감시간
    @Select("select type,closing from delivery where product_no = #{productId}")
    Map<String,String> expiredTime(int productId);

    // 점포삭제
    @Delete("delete from product where product_no=#{productId}")
    void deleteProduct(int productId);
}
