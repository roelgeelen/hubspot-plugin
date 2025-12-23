package com.differentdoors.hubspot.services;

import com.differentdoors.hubspot.models.HResults;
import com.differentdoors.hubspot.models.Objects.*;
import com.differentdoors.hubspot.models.HObject;
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
public class DealService {
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
    public Schema getDealSchema() throws Exception {
        Map<String, String> urlParams = new HashMap<>();
        urlParams.put("path", "crm/v3/schemas/deals" );

        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(URL);

        return objectMapper.readValue(restTemplate.exchange(builder.buildAndExpand(urlParams).toUri(), HttpMethod.GET, null, String.class).getBody(), Schema.class);
    }

    @Retryable(value = ResourceAccessException.class, maxAttempts = 3, backoff = @Backoff(delay = 1000))
    public HResults<HObject<Deal<String>>> searchDeals(Search search) throws Exception {
        Map<String, String> urlParams = new HashMap<>();
        urlParams.put("path", "crm/v3/objects/deals/search");

        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(URL);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Object> requestEntity = new HttpEntity<>(objectMapper.writeValueAsString(search), headers);
        return objectMapper.readValue(restTemplate.postForObject(builder.buildAndExpand(urlParams).toUri(), requestEntity, String.class), new TypeReference<HResults<HObject<Deal<String>>>>() {
        });
    }

    @Retryable(value = ResourceAccessException.class, maxAttempts = 3, backoff = @Backoff(delay = 1000))
    public HObject<Deal<String>> createDeal(HObject<Deal<String>> deal) throws Exception {
        Map<String, String> urlParams = new HashMap<>();
        urlParams.put("path", "crm/v3/objects/deals");

        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(URL);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Object> requestEntity = new HttpEntity<>(objectMapper.writeValueAsString(deal), headers);
        return objectMapper.readValue(restTemplate.exchange(builder.buildAndExpand(urlParams).toUri(), HttpMethod.POST, requestEntity, String.class).getBody(), new TypeReference<HObject<Deal<String>>>() {
        });
    }

    @Retryable(value = ResourceAccessException.class, maxAttempts = 3, backoff = @Backoff(delay = 1000))
    public HObject<Deal<String>> getDeal(String id) throws Exception {
        Map<String, String> urlParams = new HashMap<>();
        urlParams.put("path", "crm/v3/objects/deals/" + id);

        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(URL)
                .queryParam("properties", getClassProperties());

        return objectMapper.readValue(restTemplate.exchange(builder.buildAndExpand(urlParams).toUri(), HttpMethod.GET, null, String.class).getBody(), new TypeReference<HObject<Deal<String>>>() {
        });
    }

    @Retryable(value = ResourceAccessException.class, maxAttempts = 3, backoff = @Backoff(delay = 1000))
    public HObject<Deal<String>> getDeal(String id, String properties) throws Exception {
        Map<String, String> urlParams = new HashMap<>();
        urlParams.put("path", "crm/v3/objects/deals/" + id);

        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(URL)
                .queryParam("properties", properties);

        return objectMapper.readValue(restTemplate.exchange(builder.buildAndExpand(urlParams).toUri(), HttpMethod.GET, null, String.class).getBody(), HObject.class);
    }

    @Retryable(value = ResourceAccessException.class, maxAttempts = 3, backoff = @Backoff(delay = 1000))
    public HObject<Deal<String>> updateDeal(String id, HObject<Deal<String>> deal) throws Exception {
        Map<String, String> urlParams = new HashMap<>();
        urlParams.put("path", "crm/v3/objects/deals/" + id);

        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(URL);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Object> requestEntity = new HttpEntity<>(objectMapper.writeValueAsString(deal), headers);
        return objectMapper.readValue(restTemplate.exchange(builder.buildAndExpand(urlParams).toUri(), HttpMethod.PATCH, requestEntity, String.class).getBody(), new TypeReference<HObject<Deal<String>>>() {
        });
    }

    @Retryable(value = ResourceAccessException.class, maxAttempts = 3, backoff = @Backoff(delay = 1000))
    public HObject<Deal<String>> updateDealUnknown(String id, Map<String, String> properties) throws Exception {
        Map<String, String> urlParams = new HashMap<>();
        urlParams.put("path", "crm/v3/objects/deals/" + id);

        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(URL);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Object> requestEntity = new HttpEntity<>("{\"properties\": " + objectMapper.writeValueAsString(properties) + "}", headers);
        return objectMapper.readValue(restTemplate.exchange(builder.buildAndExpand(urlParams).toUri(), HttpMethod.PATCH, requestEntity, String.class).getBody(), new TypeReference<HObject<Deal<String>>>() {
        });
    }

    @Retryable(value = ResourceAccessException.class, maxAttempts = 3, backoff = @Backoff(delay = 1000))
    public HResults<AssociationV4> getDealAssociation(String id, String toObjectType) throws Exception {
        Map<String, String> urlParams = new HashMap<>();
        urlParams.put("path", "crm/v4/objects/deals/" + id + "/associations/" + toObjectType);

        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(URL);

        return objectMapper.readValue(restTemplate.exchange(builder.buildAndExpand(urlParams).toUri(), HttpMethod.GET, null, String.class).getBody(), new TypeReference<HResults<AssociationV4>>() {
        });
    }

    @Retryable(value = ResourceAccessException.class, maxAttempts = 3, backoff = @Backoff(delay = 1000))
    public void deleteDealAssociation(String id, String toObjectType, String toObjectId, String associationType) {
        Map<String, String> urlParams = new HashMap<>();
        urlParams.put("path", "crm/v3/objects/deals/" + id + "/associations/" + toObjectType + "/" + toObjectId + "/" + associationType);

        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(URL);

        restTemplate.delete(builder.buildAndExpand(urlParams).toUri());
    }

    @Recover
    public RetryException recover(Exception t) {
        return new RetryException("Maximum retries reached: " + t.getMessage());
    }

    private static String getClassProperties() {
        return Arrays.stream(Deal.class.getDeclaredFields()).map(Field::getName).collect(Collectors.joining(","));
    }

}
