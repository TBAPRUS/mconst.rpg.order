package mconst.rpg.order.models;

import mconst.rpg.order.models.dtos.OrderDto;
import mconst.rpg.order.models.entities.OrderEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface OrderMapper {
    OrderDto map(OrderEntity order);
    OrderEntity map(OrderDto order);

    List<OrderDto> map (Iterable<OrderEntity> orders);
}
