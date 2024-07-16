package mconst.rpg.order.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreatedOrderEvent {
    private Long userId;
    private Long cost;
    private Long count;
}
