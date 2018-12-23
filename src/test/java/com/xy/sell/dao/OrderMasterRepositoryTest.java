package com.xy.sell.dao;

import com.xy.sell.entity.OrderMaster;
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
public class OrderMasterRepositoryTest {

    @Autowired
    private OrderMasterRepository repository;


    @Test
    public void findByBuyerOpenid() {

        PageRequest request=new PageRequest(0,10);

        Page<OrderMaster> orderMasters=repository.findByBuyerOpenid("YX",request);
        System.out.println(orderMasters.getTotalElements());

    }

    @Test
    public void saveTest(){
        OrderMaster orderMaster = new OrderMaster();
        orderMaster.setOrderId("123456");
        orderMaster.setBuyerName("小熊");
        orderMaster.setBuyerPhone("17671682560");
        orderMaster.setBuyerAddress("上海");
        orderMaster.setBuyerOpenid("YX");
        orderMaster.setOrderAmount(new BigDecimal(12));
        OrderMaster result=repository.save(orderMaster);

    }





}