package mconst.rpg.order.controllers;

import mconst.rpg.order.models.OrderMapper;
import mconst.rpg.order.models.dtos.OrderDto;
import mconst.rpg.order.models.entities.OrderEntity;
import mconst.rpg.order.models.responses.OrdersGetResponse;
import mconst.rpg.order.services.OrderService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/orders")
public class OrderController {
    private OrderService orderService;
    private OrderMapper orderMapper;

    public OrderController(OrderService orderService, OrderMapper orderMapper) {
        this.orderService = orderService;
        this.orderMapper = orderMapper;
    }

    @GetMapping()
    public OrdersGetResponse get(
            @RequestParam(defaultValue = "20") Integer perPage,
            @RequestParam(defaultValue = "0") Integer page
    ) {
        if (perPage == 0) {
            return new OrdersGetResponse(
                    0L,
                    new ArrayList<>()
            );
        }
        Pageable pagination = PageRequest.of(page, perPage);
        var ordersPage = orderService.get(pagination);

        return new OrdersGetResponse(
                ordersPage.getTotalElements(),
                orderMapper.map(ordersPage)
        );
    }

    @PostMapping()
    public OrderEntity create(@RequestBody OrderDto order) {
        return orderService.createWithMaxCountCheck(orderMapper.map(order), 100L);
    }
}
