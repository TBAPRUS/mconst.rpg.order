package mconst.rpg.order.services;

import mconst.rpg.order.models.entities.OrderEntity;
import mconst.rpg.order.models.exceptions.ConflictException;
import mconst.rpg.order.models.exceptions.ExceptionBody;
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

        var isAvailable = this.catalogService.checkProductAvailability(order.getProductId(), sum + order.getCount());
        if (!isAvailable) {
            var exceptionBody = new ExceptionBody();
            exceptionBody.addError("not available", "product");
            throw new ConflictException(exceptionBody);
        }

        return createdOrder;
    }
}
