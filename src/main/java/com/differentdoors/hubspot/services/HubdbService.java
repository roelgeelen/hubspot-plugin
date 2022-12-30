package com.differentdoors.hubspot.services;

import com.differentdoors.hubspot.models.HResults;
import com.differentdoors.hubspot.models.HubDB.HubTable;
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

import java.util.HashMap;
import java.util.Map;

@Service
public class HubdbService {
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
    public HResults<HubTable<?>> getDBRows(int tableId, String filter, String filterValue) throws Exception {
        Map<String, String> urlParams = new HashMap<>();
        urlParams.put("path", "cms/v3/hubdb/tables/" + tableId + "/rows");

        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(URL);

        if (filter != null) {
            builder.queryParam(filter, filterValue);
        }

        return objectMapper.readValue(restTemplate.getForObject(builder.buildAndExpand(urlParams).toUri(), String.class), new TypeReference<HResults<HubTable<?>>>() {
        });
    }

    @Retryable(value = ResourceAccessException.class, maxAttempts = 3, backoff = @Backoff(delay = 1000))
    public HubTable<?> getDBRow(int tableId, String rowId) throws Exception {
        Map<String, String> urlParams = new HashMap<>();
        urlParams.put("path", "cms/v3/hubdb/tables/" + tableId + "/rows/" + rowId);

        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(URL);

        return objectMapper.readValue(restTemplate.getForObject(builder.buildAndExpand(urlParams).toUri(), String.class), new TypeReference<HubTable<?>>() {
        });
    }

    @Retryable(value = ResourceAccessException.class, maxAttempts = 3, backoff = @Backoff(delay = 1000))
    public HubTable<?> createDBRow(int tableId, HubTable<?> tableRow) throws Exception {
        Map<String, String> urlParams = new HashMap<>();
        urlParams.put("path", "cms/v3/hubdb/tables/" + tableId + "/rows");

        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(URL);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Object> requestEntity = new HttpEntity<>(objectMapper.writeValueAsString(tableRow), headers);
        HubTable<?> d = restTemplate.postForObject(builder.buildAndExpand(urlParams).toUri(), requestEntity, HubTable.class);
        publishTable(tableId);
        return d;
    }

    @Retryable(value = ResourceAccessException.class, maxAttempts = 3, backoff = @Backoff(delay = 1000))
    public void updateDBRow(int tableId, String rowId, HubTable<?> tableRow) throws Exception {
        Map<String, String> urlParams = new HashMap<>();
        urlParams.put("path", "cms/v3/hubdb/tables/" + tableId + "/rows/" + rowId + "/draft");

        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(URL);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Object> requestEntity = new HttpEntity<>(objectMapper.writeValueAsString(tableRow), headers);
        restTemplate.exchange(builder.buildAndExpand(urlParams).toUri(), HttpMethod.PATCH, requestEntity, String.class);
        publishTable(tableId);
    }

    @Retryable(value = ResourceAccessException.class, maxAttempts = 3, backoff = @Backoff(delay = 1000))
    public void deleteDBRow(int tableId, String rowId) {
        Map<String, String> urlParams = new HashMap<>();
        urlParams.put("path", "cms/v3/hubdb/tables/" + tableId + "/rows/" + rowId + "/draft");

        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(URL);

        restTemplate.exchange(builder.buildAndExpand(urlParams).toUri(), HttpMethod.DELETE, null, String.class);
        publishTable(tableId);
    }

    @Retryable(value = ResourceAccessException.class, maxAttempts = 3, backoff = @Backoff(delay = 1000))
    public void publishTable(int tableId) {
        Map<String, String> urlParams = new HashMap<>();
        urlParams.put("path", "cms/v3/hubdb/tables/" + tableId + "/draft/publish");

        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(URL);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Object> requestEntityPublish = new HttpEntity<>(null, headers);
        restTemplate.postForObject(builder.buildAndExpand(urlParams).toUri(), requestEntityPublish, String.class);
    }

    @Recover
    public RetryException recover(Exception t){
        return new RetryException("Maximum retries reached: " + t.getMessage());
    }
}
