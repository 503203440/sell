package com.xy.sell.service.impl;

import com.xy.sell.dto.OrderDTO;
import com.xy.sell.entity.OrderDetail;
import com.xy.sell.entity.OrderMaster;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class OrderServiceImplTest {

    @Autowired
    private OrderServiceImpl orderService;

    private final String BUYER_OPENID="00142xff4232x923";
    private final String ORDER_ID="1539218952858259059";

    @Test
    public void create() {
        OrderDTO orderDTO=new OrderDTO();
        orderDTO.setBuyerName("小熊");
        orderDTO.setBuyerAddress("上海市XX区XX街XX弄XX号");
        orderDTO.setBuyerPhone("17671682560");
        orderDTO.setBuyerOpenid(BUYER_OPENID);
        //购物车
        List<OrderDetail> orderDetailList = new ArrayList<>();
        OrderDetail o1 = new OrderDetail();
        o1.setProductId("123456");
        o1.setProductQuantity(1);

        OrderDetail o2 = new OrderDetail();
        o2.setProductId("123458");
        o2.setProductQuantity(99);

        orderDetailList.add(o1);
        orderDetailList.add(o2);

        orderDTO.setOrderDetailList(orderDetailList);

        OrderDTO result = orderService.create(orderDTO);
        log.info("【创建订单】result={}", result);
        Assert.assertNotNull(result);
    }

    @Test
    public void findOne() {
        OrderDTO orderDTO = orderService.findOne(ORDER_ID);
        log.info("【对象信息】:"+orderDTO.toString());

    }

    @Test
    public void findList() {
        PageRequest request=new PageRequest(0,10);
        Page<OrderDTO> orderDTOPage = orderService.findList(BUYER_OPENID, request);
        orderDTOPage.getContent().forEach(e-> System.out.println(e.getBuyerName()));
    }

    @Test
    public void cancel() {
        OrderDTO orderDTO = orderService.findOne(ORDER_ID);

        OrderDTO cancel = orderService.cancel(orderDTO);

        System.out.println(cancel.toString());
    }

    @Test
    public void finish() {
        OrderDTO orderDTO = orderService.findOne(ORDER_ID);

        OrderDTO cancel = orderService.finish(orderDTO);

        System.out.println(cancel.toString());
    }

    @Test
    public void paid() {

        OrderDTO orderDTO = orderService.findOne(ORDER_ID);

        OrderDTO cancel = orderService.paid(orderDTO);

        System.out.println(cancel.toString());


    }
}