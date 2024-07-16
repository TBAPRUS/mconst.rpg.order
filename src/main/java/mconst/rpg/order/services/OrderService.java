package mconst.rpg.order.services;

import mconst.rpg.order.models.dtos.CreatedOrderEvent;
import mconst.rpg.order.models.entities.OrderEntity;
import mconst.rpg.order.models.exceptions.ConflictException;
import mconst.rpg.order.models.exceptions.ExceptionBody;
import mconst.rpg.order.repositories.OrderRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderService {
    private OrderRepository orderRepository;
    private CatalogService catalogService;
    private AccountService accountService;
    private UuidGeneratorService uuidGeneratorService;
    private KafkaTemplate<String, CreatedOrderEvent> kafkaTemplate;

    public OrderService(
            OrderRepository orderRepository,
            CatalogService catalogService,
            AccountService accountService,
            UuidGeneratorService uuidGeneratorService,
            KafkaTemplate<String, CreatedOrderEvent> kafkaTemplate
    ) {
        this.orderRepository = orderRepository;
        this.catalogService = catalogService;
        this.accountService = accountService;
        this.uuidGeneratorService = uuidGeneratorService;
        this.kafkaTemplate = kafkaTemplate;
    }

    public Page<OrderEntity> get(Pageable pagination) {
        return this.orderRepository.findAll(pagination);
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public OrderEntity create(OrderEntity order) {
        var sum = this.orderRepository.getSumCountByProductId(order.getProductId()).orElse(0L);
        var createdOrder = this.orderRepository.save(order);

        var isAvailable = this.catalogService.checkProductAvailability(order.getProductId(), sum + order.getCount(), 2990L);
        if (!isAvailable) {
            var exceptionBody = new ExceptionBody();
            exceptionBody.addError("not available", "product");
            throw new ConflictException(exceptionBody);
        }

        Integer cost = 1000;
        String token = this.uuidGeneratorService.generate();
        this.accountService.spendMoney(Math.toIntExact(order.getUserId()), cost, token);

        var event = new CreatedOrderEvent(
            order.getUserId(),
            Long.valueOf(cost),
            order.getCount()
        );
        kafkaTemplate.send("orders.created", event);

        return createdOrder;
    }
}
