package com.xy.sell.service.impl;

import com.xy.sell.entity.ProductInfo;
import com.xy.sell.enums.ProductStatusEnum;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class ProductServiceImplTest {

    @Autowired
    private ProductServiceImpl productService;


    @Test
    public void findOne() {
        ProductInfo productInfo = productService.findOne("123456");
        log.info("略略略😜");
        log.info("productInfo的内容为："+ productInfo.toString());
    }

    @Test
    public void findUpAll() {
        List<ProductInfo> productInfoList =productService.findUpAll();
        log.info("productInfoList.size()="+productInfoList.size());
    }

    @Test
    public void findAll() {
        PageRequest request=new PageRequest(0,2);
        Page<ProductInfo> productInfoPage=productService.findAll(request);
        log.info("productInfoPage.getTotalElements()="+productInfoPage.getTotalElements());
    }

    @Test
    public void save() {
        ProductInfo p = new ProductInfo();
        p.setProductId("123457");
        p.setProductName("皮皮虾");
        p.setProductPrice(new BigDecimal(3.2));
        p.setProductStock(23);
        p.setProductDescription("可以");
        p.setProductIcon("http://dafs.jpg");
        p.setProductStatus(ProductStatusEnum.下架.getCode());
        p.setCategoryType(2);
        ProductInfo productInfo=productService.save(p);
        log.info("p对象的完整信息"+p.toString());
    }
}