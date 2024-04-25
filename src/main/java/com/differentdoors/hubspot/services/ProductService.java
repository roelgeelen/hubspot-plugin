package com.differentdoors.hubspot.services;

import com.differentdoors.hubspot.models.HObject;
import com.differentdoors.hubspot.models.HResults;
import com.differentdoors.hubspot.models.Objects.Product;
import com.differentdoors.hubspot.models.Batch.Batch;
import com.differentdoors.hubspot.models.Search.Search;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.retry.RetryException;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;
import java.util.Map;

@Service
public class ProductService {
    @Value("${different_doors.hubspot.url}")
    private String URL;


    private final ObjectMapper objectMapper = JsonMapper.builder()
            .findAndAddModules()
            .serializationInclusion(JsonInclude.Include.NON_NULL)
            .build();

    @Autowired
    @Qualifier("Hubspot")
    private RestTemplate restTemplate;

    @Retryable(value = ResourceAccessException.class, maxAttempts = 3, backoff = @Backoff(delay = 1000))
    public HResults<HObject<Product>> searchProduct(Search search) throws Exception {
        Map<String, String> urlParams = new HashMap<>();
        urlParams.put("path", "crm/v3/objects/products/search");

        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(URL);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Object> requestEntity = new HttpEntity<>(objectMapper.writeValueAsString(search), headers);
        return objectMapper.readValue(restTemplate.postForObject(builder.buildAndExpand(urlParams).toUri(), requestEntity, String.class), new TypeReference<HResults<HObject<Product>>>() {
        });
    }

    @Retryable(value = ResourceAccessException.class, maxAttempts = 3, backoff = @Backoff(delay = 1000))
    public HResults<HObject<Product>> searchBatchProducts(Batch batch) throws Exception {
        Map<String, String> urlParams = new HashMap<>();
        urlParams.put("path", "crm/v3/objects/products/batch/read");

        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(URL);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Object> requestEntity = new HttpEntity<>(objectMapper.writeValueAsString(batch), headers);
        return objectMapper.readValue(restTemplate.postForObject(builder.buildAndExpand(urlParams).toUri(), requestEntity, String.class), new TypeReference<HResults<HObject<Product>>>() {
        });
    }

    @Recover
    public RetryException recover(Exception t){
        return new RetryException("Maximum retries reached: " + t.getMessage());
    }
}
