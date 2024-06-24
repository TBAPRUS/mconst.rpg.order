package mconst.rpg.order.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import mconst.rpg.order.models.pojos.CatalogCheckProductAvailability;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CatalogService {
    private String scheme;
    private String host;
    private String port;

    public CatalogService() {
        this.scheme = "http";
        this.host = "localhost";
        this.port = "8081";
    }

    private String getUrl(String endpoint) {
        return String.format("%s://%s:%s/%s", scheme, host, port, endpoint);
    }

    public Boolean checkProductAvailability(Long id, Long count) {
        var restTemplate = new RestTemplate();
        var url = getUrl(String.format("/products/%s/check-product-availability", id));
        var request = new HttpEntity<>(new CatalogCheckProductAvailability(count));
        var response = restTemplate.exchange(url, HttpMethod.POST, request, String.class);

        var mapper = new ObjectMapper();
        try {
            JsonNode root = mapper.readTree(response.getBody());
            JsonNode success = root.path("success");
            return success.asBoolean();
        } catch (JsonProcessingException e) {
            return false;
        }
    }
}
