package mconst.rpg.order.models.pojos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountCompensateSpendingMoney implements Serializable {
    private String token;
}
