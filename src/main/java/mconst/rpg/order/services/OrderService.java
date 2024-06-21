package mconst.rpg.order.services;

import mconst.rpg.order.models.entities.OrderEntity;
import mconst.rpg.order.repositories.OrderRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderService {
    private OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Page<OrderEntity> get(Pageable pagination) {
        return this.orderRepository.findAll(pagination);
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public OrderEntity createWithMaxCountCheck(OrderEntity order, Long countInCatalog) {
        var sum = this.orderRepository.getSumCountByProductId(order.getProductId());
        if (sum.orElse(0L) + order.getCount() > countInCatalog) {
            throw new RuntimeException();
        }
        return this.orderRepository.save(order);
    }
}
