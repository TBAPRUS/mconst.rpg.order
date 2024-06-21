package mconst.rpg.order.models.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import mconst.rpg.order.models.dtos.OrderDto;

import java.util.List;

@Data
@AllArgsConstructor
public class OrdersGetResponse {
    private Long total;
    private List<OrderDto> orders;
}
