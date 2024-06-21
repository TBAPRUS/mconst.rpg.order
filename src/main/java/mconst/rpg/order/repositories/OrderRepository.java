package mconst.rpg.order.repositories;

import mconst.rpg.order.models.entities.OrderEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface OrderRepository extends CrudRepository<OrderEntity, Long>, PagingAndSortingRepository<OrderEntity, Long> {

    @Query("SELECT SUM(o.count) FROM OrderEntity AS o WHERE o.productId = ?1")
    Optional<Long> getSumCountByProductId(Long productId);
}

