package mconst.rpg.order.models.pojos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountSpendMoney implements Serializable {
    private Integer money;
    private String token;
}
