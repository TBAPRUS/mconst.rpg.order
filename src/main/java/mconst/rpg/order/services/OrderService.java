package mconst.rpg.order.services;

import mconst.rpg.order.models.entities.OrderEntity;
import mconst.rpg.order.repositories.OrderRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class OrderService {
    private OrderRepository orderRepository;
    private CatalogService catalogService;

    public OrderService(OrderRepository orderRepository, CatalogService catalogService) {
        this.orderRepository = orderRepository;
        this.catalogService = catalogService;
    }

    public Page<OrderEntity> get(Pageable pagination) {
        return this.orderRepository.findAll(pagination);
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public OrderEntity create(OrderEntity order) {
        var sum = this.orderRepository.getSumCountByProductId(order.getProductId()).orElse(0L);
        var createdOrder = this.orderRepository.save(order);
//        try {
            var isAvaliable = this.catalogService.checkProductAvailability(order.getProductId(), sum + order.getCount());
            if (!isAvaliable) {
                throw new RuntimeException();
            }

            return createdOrder;
//        } catch (RuntimeException exception) {
//            throw new RuntimeException();
//        }
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
