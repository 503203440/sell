package com.xy.sell.dao;

import com.xy.sell.entity.ProductCategory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductCategoryRepositoryTest {

    @Autowired
    private ProductCategoryRepository repository;

    /*查询全部测试*/
    @Test
    public void findOneTest() {
        List<ProductCategory> productCategory = repository.findAll();
        for (ProductCategory p : productCategory) {
            System.out.println(p.toString());
        }
    }

    /*新增测试*/
    @Test
    public void save(){
        ProductCategory productCategory=new ProductCategory();

        productCategory.setCategoryName("男生最爱");
        productCategory.setCategoryType(2);

        ProductCategory result=repository.save(productCategory);
    }

    @Test
    public void findByCategoryTypeInTest(){
        List<Integer> list= Arrays.asList(2,3,4);

        List<ProductCategory> result=repository.findByCategoryTypeIn(list);

        for (ProductCategory p :result) {
            System.out.println(p.toString());
        }

    }


}