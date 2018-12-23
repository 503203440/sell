package com.xy.sell.service.impl;

import com.xy.sell.entity.ProductCategory;
import com.xy.sell.service.CategoryService;
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
public class CategoryServiceImplTest {

    @Autowired
    private CategoryServiceImpl categoryService;

    @Test
    public void findOne() {
        ProductCategory productCategory= categoryService.findOne(2);
        System.out.println(productCategory);
    }

    @Test
    public void findAll() {
        List<ProductCategory> list=categoryService.findAll();
        for (ProductCategory p :
                list) {
            System.out.println(p);
        }
    }

    @Test
    public void findByCategoryTypeIn() {
        List<Integer> params= Arrays.asList(0,1,2,3,4,5);
        List<ProductCategory> list=categoryService.findByCategoryTypeIn(params);
        for (ProductCategory p :
                list) {
            System.out.println(p);
        }
    }

    @Test
    public void save() {
        ProductCategory productCategory=new ProductCategory();
        productCategory.setCategoryType(6);
        productCategory.setCategoryName("这是个测试数据");
        categoryService.save(productCategory);

    }
}