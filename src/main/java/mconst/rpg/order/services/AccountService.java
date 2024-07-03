package mconst.rpg.order.services;

import lombok.extern.slf4j.Slf4j;
import mconst.rpg.order.models.exceptions.ExceptionBody;
import mconst.rpg.order.models.exceptions.InternalServerException;
import mconst.rpg.order.models.exceptions.NotFoundException;
import mconst.rpg.order.models.pojos.AccountCompensateSpendingMoney;
import mconst.rpg.order.models.pojos.AccountSpendMoney;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
public class AccountService {
    private String scheme;
    private String host;
    private String port;

    public AccountService() {
        this.scheme = "http";
        this.host = "localhost";
        this.port = "8082";
    }

    private String getUrl(String endpoint) {
        return String.format("%s://%s:%s%s", scheme, host, port, endpoint);
    }

    public void spendMoney(Integer id, Integer money, String token) {
        var restTemplate = new RestTemplate();
        var url = getUrl(String.format("/accounts/%s/spend", id));
        var request = new HttpEntity<>(new AccountSpendMoney(money, token));
        try {
            restTemplate.postForLocation(url, request);
        } catch (HttpClientErrorException exception) {
            throw new NotFoundException(exception.getResponseBodyAs(ExceptionBody.class));
        } catch (Exception exception) {
            log.info("Unhandled exception", exception);
            throw new InternalServerException();
        }
    }

    public void compensateSpendingMoney(Integer id, String token) {
        var restTemplate = new RestTemplate();
        var url = getUrl(String.format("/accounts/%s/spend", id));
        var request = new HttpEntity<>(new AccountCompensateSpendingMoney(token));
        try {
            restTemplate.postForLocation(url, request);
        } catch (HttpClientErrorException exception) {
            throw new NotFoundException(exception.getResponseBodyAs(ExceptionBody.class));
        } catch (Exception exception) {
            log.info("Unhandled exception", exception);
            throw new InternalServerException();
        }
    }
}
