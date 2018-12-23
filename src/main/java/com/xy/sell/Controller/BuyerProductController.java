package com.xy.sell.Controller;

import com.xy.sell.VO.ProductInfoVO;
import com.xy.sell.VO.ProductVO;
import com.xy.sell.VO.ResultVO;
import com.xy.sell.entity.ProductCategory;
import com.xy.sell.entity.ProductInfo;
import com.xy.sell.service.CategoryService;
import com.xy.sell.service.ProductService;
import com.xy.sell.util.ResultVOUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 买家商品
 */
@RestController
@RequestMapping("/buyer/product")
public class BuyerProductController {


    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;


    @GetMapping("/list")
    public Object List(){

        //1，查询所有上架商品
        List<ProductInfo> productInfoList = productService.findUpAll();

        //2，查询类目（一次性查询）
        List<Integer> categoryTypeList=new ArrayList<>();
        //传统方法
        //将所有上架商品的类型id取得存入list
        for(ProductInfo p:productInfoList){
            categoryTypeList.add(p.getCategoryType());
        }
        //精简方法（java8，lambda表达式）
//        productInfoList.stream().map(e -> e.getCategoryType()).collect(Collectors.toList());

        //根据上架商品类型id查询商品产品种类集合
        List<ProductCategory> productCategoryList = categoryService.findByCategoryTypeIn(categoryTypeList);

        System.out.println("productCategoryList的大小为："+productCategoryList.size());

        //3，数据拼装
        List<ProductVO> productVOList=new ArrayList<>();
        for(ProductCategory productCategory:productCategoryList){

            ProductVO productVO=new ProductVO();
            productVO.setCategoryType(productCategory.getCategoryType());
            productVO.setCategoryName(productCategory.getCategoryName());

            List<ProductInfoVO> productInfoVOList=new ArrayList<>();
            for (ProductInfo productInfo:productInfoList){
                if(productInfo.getCategoryType().equals(productCategory.getCategoryType())){

                    ProductInfoVO productInfoVO=new ProductInfoVO();

                    BeanUtils.copyProperties(productInfo,productInfoVO);

                    productInfoVOList.add(productInfoVO);
                }
            }
            productVO.setProductInfoVOList(productInfoVOList);
            productVOList.add(productVO);
        }

        //封装对象并返回成功信息
        return ResultVOUtil.success(productVOList);
    }


}
