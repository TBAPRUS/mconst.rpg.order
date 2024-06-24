package mconst.rpg.order.models.pojos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CatalogCheckProductAvailability implements Serializable {
    private Long count;
}
