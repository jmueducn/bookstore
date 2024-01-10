package com.example.bookdemo.listener;

import com.example.bookdemo.DAO.BookDAO;
import com.example.bookdemo.DTO.OrderRequest;
import com.example.bookdemo.configuration.WebSocketServer;
import com.example.bookdemo.entity.Order;
import com.example.bookdemo.service.OrderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
public class OrderListener {
    @Autowired
    private OrderService orderService;
    @Autowired
    private BookDAO bookDAO;
    @Autowired
    private WebSocketServer ws;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final SimpMessagingTemplate messagingTemplate;
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public OrderListener(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @KafkaListener(topics = "topic1",groupId = "group_topic_test")
    public void topicListener(String  request) {
        try {

            // 将订单信息字符串转换为OrderRequest对象
            OrderRequest orderRequest = objectMapper.readValue(request, OrderRequest.class);
            System.out.println( orderRequest.toString());
            // 调用OrderService处理订单
            Order order =orderService.placeOrder(orderRequest);

            long id=order.getId();
            long uid=order.getUserId();
            String message=String.valueOf(id)+":"+String.valueOf(uid);
            kafkaTemplate.send("topic2",  "key", message);
        } catch (Exception e) {
            // 处理反序列化异常
            e.printStackTrace();
        }


    }
    @KafkaListener(topics = "topic2",groupId = "group_topic_test")
    public void topic2Listener(String  request) {
        try {
            // 将订单信息字符串转换为OrderRequest对象
            String[] parts = request.split(":");
            String orderId=parts[0];
            String userId=parts[1];
            String msg= "orderId="+orderId+" by "+userId+" is Successfully placed ";

            ws.sendMessageToUser(userId,msg);
            bookDAO.flashBookRedis();
            // 调用OrderService处理订单
        } catch (Exception e) {
            // 处理反序列化异常
            e.printStackTrace();
        }


    }
}
