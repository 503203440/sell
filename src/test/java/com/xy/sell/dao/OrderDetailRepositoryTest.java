package com.xy.sell.dao;

import com.xy.sell.entity.OrderDetail;
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
public class OrderDetailRepositoryTest {

    @Autowired
    private OrderDetailRepository repository;
    
    
    @Test
    public void findByOrderId() {
        List<OrderDetail> orderDetails=repository.findByOrderId("123456");
        System.out.println(orderDetails.size());
    }

    @Test
    public void saveTest(){
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setDetailId("12345679");
        orderDetail.setOrderId("123456");
        orderDetail.setProductId("123456");
        orderDetail.setProductName("皮蛋粥");
        orderDetail.setProductPrice(new BigDecimal(3.2));
        orderDetail.setProductQuantity(2);
        orderDetail.setProductIcon("www.xxx.com/aaa.png");

        OrderDetail result=repository.save(orderDetail);
        System.out.println(result);
    }
    
}