package com.xy.sell.dao;

import com.xy.sell.entity.ProductInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductInfoRepositoryTest {

    @Autowired
    private ProductInfoRepository repository;


    @Test
    public void savaTest(){
        ProductInfo p=new ProductInfo();
        p.setProductId("123456");
        p.setProductName("abc");
        p.setProductPrice(new BigDecimal(3.2));
        p.setProductStock(23);
        p.setProductDescription("可以");
        p.setProductIcon("http://dafs.jpg");
        p.setProductStatus(1);
        p.setCategoryType(2);

        repository.save(p);

    }




    @Test
    public void findByProductStatus() {
        List<ProductInfo> list=repository.findByProductStatus(1);
        System.out.println("状态为1正常的个数为："+list.size());
    }
}