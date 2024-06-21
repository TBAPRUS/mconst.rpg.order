package mconst.rpg.order;

import mconst.rpg.order.models.OrderMapper;
import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfiguration {
    @Bean
    public OrderMapper orderMapper() {
        return Mappers.getMapper(OrderMapper.class);
    }
}
