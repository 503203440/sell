package com.xy.sell.entity;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;

/**
 * 类目
 */
@Entity//标明是实体类
@Table(name = "product_category")//注解表名
@DynamicUpdate//动态更新
@Data//lombok工具省去getter和setter
public class ProductCategory {

    /*类目id*/
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //自增长
    private Integer categoryId;

    /*类目名字*/
    private String categoryName;

    /*类目编号*/
    private Integer categoryType;

}
