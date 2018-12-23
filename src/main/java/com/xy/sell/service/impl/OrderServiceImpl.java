package com.xy.sell.service.impl;

import com.xy.sell.converter.OrderMaster2OrderDTOConverter;
import com.xy.sell.dao.OrderDetailRepository;
import com.xy.sell.dao.OrderMasterRepository;
import com.xy.sell.dto.CartDTO;
import com.xy.sell.dto.OrderDTO;
import com.xy.sell.entity.OrderDetail;
import com.xy.sell.entity.OrderMaster;
import com.xy.sell.entity.ProductInfo;
import com.xy.sell.enums.OrderStatusEnum;
import com.xy.sell.enums.PayStatusEnum;
import com.xy.sell.enums.ResultEnum;
import com.xy.sell.exception.SellException;
import com.xy.sell.service.OrderService;
import com.xy.sell.service.ProductService;
import com.xy.sell.util.KeyUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    @Autowired
    private ProductService productService;
    @Autowired
    private OrderDetailRepository orderDetailRepository;
    @Autowired
    private OrderMasterRepository orderMasterRepository;

    @Override
    @Transactional
    public OrderDTO create(OrderDTO orderDTO) {

        //生成订单id
        String orderId=KeyUtil.genUniqueKey();

        BigDecimal orderAmount=new BigDecimal(BigInteger.ZERO);

        //1,查询商品（数量，价格）
        for (OrderDetail orderDetail:orderDTO.getOrderDetailList()) {
            ProductInfo productInfo = productService.findOne(orderDetail.getProductId());
            if(productInfo==null){
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }
            //2，计算总价
            orderAmount=productInfo.getProductPrice()
                    .multiply(new BigDecimal(orderDetail.getProductQuantity()))
                    .add(orderAmount);
            //3，写入订单数据库（orderDetaii）
            BeanUtils.copyProperties(productInfo,orderDetail);
            orderDetail.setDetailId(KeyUtil.genUniqueKey());
            orderDetail.setOrderId(orderId);
            orderDetailRepository.save(orderDetail);
        }
        //3，写入订单数据库（orderMaster）
        OrderMaster orderMaster=new OrderMaster();
        orderDTO.setOrderId(orderId);
        BeanUtils.copyProperties(orderDTO,orderMaster);
        orderMaster.setOrderAmount(orderAmount);
        orderMaster.setOrderStatus(OrderStatusEnum.NEW.getCode());
        orderMaster.setPayStatus(PayStatusEnum.WAIT.getCode());
        //写入数据库
        OrderMaster resultOrderMaster = orderMasterRepository.save(orderMaster);
        //将返回的id设置给orderDTO
        orderDTO.setOrderId(resultOrderMaster.getOrderId());

        //4，扣库存
        List<CartDTO> cartDTOList=new ArrayList<>();

        //lambda表达式，遍历orderDTO.getOrderDetailList()
        cartDTOList=orderDTO.getOrderDetailList().stream().map(e->
                new CartDTO(e.getProductId(),e.getProductQuantity())).collect(Collectors.toList());

        productService.decreaseStock(cartDTOList);

        return orderDTO;
    }

    @Override
    public OrderDTO findOne(String orderId) {
        OrderMaster orderMaster = orderMasterRepository.findById(orderId).orElse(null);
        if (orderMaster == null) {
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }

        List<OrderDetail> orderDetailList = orderDetailRepository.findByOrderId(orderId);
        if(CollectionUtils.isEmpty(orderDetailList)){
            throw new SellException(ResultEnum.ORDERDETAIL_NOT_EXIST);
        }

        OrderDTO orderDTO=new OrderDTO();

        BeanUtils.copyProperties(orderMaster,orderDTO);
        orderDTO.setOrderDetailList(orderDetailList);
        return orderDTO;
    }

    @Override
    public Page<OrderDTO> findList(String buyerOpenid, Pageable pageable) {

        //得到带分页的订单
        Page<OrderMaster> orderMasterPage=orderMasterRepository.findByBuyerOpenid(buyerOpenid,pageable);

        //将订单集合转化为OrderDTO集合
        List<OrderDTO> orderDTOList = OrderMaster2OrderDTOConverter.convert(orderMasterPage.getContent());

        //将OrderDTO集合构造为Page分页对象
        Page<OrderDTO> orderDTOPage=new PageImpl<>(orderDTOList,pageable,orderMasterPage.getTotalElements());

        //返回带分页的OrderDTO
        return orderDTOPage;
    }

    @Override
    @Transactional
    public OrderDTO cancel(OrderDTO orderDTO) {

        OrderMaster orderMaster=new OrderMaster();

        //判断订单状态
        if(!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())){
            log.error("【取消订单】订单状态不正确，orderId={}，orderStatus={}", orderDTO.getOrderId(), orderDTO.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_STUTAS_ERROR);
        }
        //修改订单状态
        orderDTO.setOrderStatus(OrderStatusEnum.CANCEL.getCode());
        BeanUtils.copyProperties(orderDTO,orderMaster);
        OrderMaster updateResult = orderMasterRepository.save(orderMaster);
        if (updateResult==null){
            log.error("【取消订单】，更新失败，orderMaster={}", orderMaster.toString());
            throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
        }
        //返还库存
        if (CollectionUtils.isEmpty(orderDTO.getOrderDetailList())){
            log.error("【取消订单】，订单中无商品详情，orderDTO={}",orderDTO.toString());;
            throw new SellException(ResultEnum.ORDER_DETAIL_EMPTY);
        }
        List<CartDTO> cartDTOList=orderDTO.getOrderDetailList().stream()
                .map(e-> new CartDTO(e.getProductId(),e.getProductQuantity()))
                .collect(Collectors.toList());
        productService.increaseStock(cartDTOList);
        //如果已支付（退款）
        if(orderDTO.getPayStatus().equals(PayStatusEnum.SUCCESS.getCode())){
            //TODO

        }

        return orderDTO;
    }

    @Override
    public OrderDTO finish(OrderDTO orderDTO) {

        //判断订单状态
        if(!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())){
            log.error("【完结订单】订单状态不正确，orderId={}，orderStatus={}", orderDTO.getOrderId(), orderDTO.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_STUTAS_ERROR);
        }

        //修改订单状态
        orderDTO.setOrderStatus(OrderStatusEnum.FINISHED.getCode());
        OrderMaster orderMaster=new OrderMaster();
        BeanUtils.copyProperties(orderDTO,orderMaster);

        OrderMaster updateResult = orderMasterRepository.save(orderMaster);
        if(updateResult==null){
            log.error("【完结订单】，更新失败，orderMaster={}", orderMaster.toString());
            throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
        }

        return orderDTO;
    }

    @Override
    @Transactional
    public OrderDTO paid(OrderDTO orderDTO) {


        //判断订单状态
        if(!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())){
            log.error("【支付订单】订单状态不正确，orderId={}，orderStatus={}", orderDTO.getOrderId(), orderDTO.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_STUTAS_ERROR);
        }

        //判断支付状态
        if(!orderDTO.getPayStatus().equals(PayStatusEnum.WAIT.getCode())){
            log.error("【支付订单】订单支付状态不正确，orderDTO={}", orderDTO.toString());
            throw new SellException(ResultEnum.ORDER_PAY_STATUS_ERROR);
        }

        //修改订单状态
        orderDTO.setPayStatus(PayStatusEnum.SUCCESS.getCode());
        OrderMaster orderMaster=new OrderMaster();
        BeanUtils.copyProperties(orderDTO,orderMaster);
        OrderMaster updateResult = orderMasterRepository.save(orderMaster);
        if(updateResult==null){
            log.error("【支付订单】更新失败，orderMaster={}",orderMaster.toString());
            throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
        }

        return orderDTO;
    }
}
