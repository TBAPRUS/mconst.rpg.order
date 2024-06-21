package mconst.rpg.order.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {
    private Integer id;

    private Integer userId;
    private Integer productId;
    private Integer count;
}

