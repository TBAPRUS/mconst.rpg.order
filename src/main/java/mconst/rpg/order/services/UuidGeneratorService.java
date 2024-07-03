package mconst.rpg.order.services;

import org.springframework.stereotype.Service;

@Service
public class UuidGeneratorService {
    private Long i;

    public UuidGeneratorService() {
        this.i = 0L;
    }

    public String generate() {
        return (i++).toString();
    }
}
