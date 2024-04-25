package com.differentdoors.hubspot.services;

import com.differentdoors.hubspot.models.Batch.Batch;
import com.differentdoors.hubspot.models.HObject;
import com.differentdoors.hubspot.models.HResults;
import com.differentdoors.hubspot.models.Objects.LineItem;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.retry.RetryException;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class LineItemService {
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
    public HObject<LineItem> createLineItem(HObject<LineItem> lineItem) throws Exception {
        Map<String, String> urlParams = new HashMap<>();
        urlParams.put("path", "crm/v3/objects/line_items");

        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(URL);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Object> requestEntity = new HttpEntity<>(objectMapper.writeValueAsString(lineItem), headers);
        return objectMapper.readValue(restTemplate.postForObject(builder.buildAndExpand(urlParams).toUri(), requestEntity, String.class), new TypeReference<HObject<LineItem>>() {
        });
    }

    @Retryable(value = ResourceAccessException.class, maxAttempts = 3, backoff = @Backoff(delay = 1000))
    public HResults<HObject<LineItem>> createBatchLineItem(Batch<HObject<LineItem>> lineItems) throws Exception {
        Map<String, String> urlParams = new HashMap<>();
        urlParams.put("path", "crm/v3/objects/line_items/batch/create");

        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(URL);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Object> requestEntity = new HttpEntity<>(objectMapper.writeValueAsString(lineItems), headers);
        return objectMapper.readValue(restTemplate.postForObject(builder.buildAndExpand(urlParams).toUri(), requestEntity, String.class), new TypeReference<HResults<HObject<LineItem>>>() {
        });
    }

    @Retryable(value = ResourceAccessException.class, maxAttempts = 3, backoff = @Backoff(delay = 1000))
    public HObject<LineItem> getLineItem(String id) throws Exception {
        Map<String, String> urlParams = new HashMap<>();
        urlParams.put("path", "crm/v3/objects/line_items/" + id);

        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(URL)
                .queryParam("properties", getClassProperties());;

        return objectMapper.readValue(restTemplate.getForObject(builder.buildAndExpand(urlParams).toUri(), String.class), new TypeReference<HObject<LineItem>>() {
        });
    }

    @Retryable(value = ResourceAccessException.class, maxAttempts = 3, backoff = @Backoff(delay = 1000))
    public void associateLineItem(String lineItemID, String toObjectType, String toObjectId, String associationType) {
        Map<String, String> urlParams = new HashMap<>();
        urlParams.put("path", "crm/v3/objects/line_items/" + lineItemID + "/associations/" + toObjectType + "/" + toObjectId + "/" + associationType);

        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(URL);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Object> requestEntity = new HttpEntity<>(null, headers);
        restTemplate.exchange(builder.buildAndExpand(urlParams).toUri(), HttpMethod.PUT, requestEntity, String.class);
    }

    @Recover
    public RetryException recover(Exception t){
        return new RetryException("Maximum retries reached: " + t.getMessage());
    }

    private static String getClassProperties() {
        return Arrays.stream(LineItem.class.getDeclaredFields()).map(Field::getName).collect(Collectors.joining(","));
    }
}
