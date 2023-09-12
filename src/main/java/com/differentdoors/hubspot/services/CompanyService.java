package com.differentdoors.hubspot.services;

import com.differentdoors.hubspot.models.HObject;
import com.differentdoors.hubspot.models.Objects.Company;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
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
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CompanyService {
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
    public HObject<Company<String>> getCompany(String id) throws Exception {
        Map<String, String> urlParams = new HashMap<>();
        urlParams.put("path", "crm/v3/objects/companies/" + id);

        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(URL)
                .queryParam("properties", getClassProperties());

        return objectMapper.readValue(restTemplate.getForObject(builder.buildAndExpand(urlParams).toUri(), String.class), new TypeReference<HObject<Company<String>>>() {});
    }

    private static String getClassProperties() {
        return Arrays.stream(Company.class.getDeclaredFields()).map(Field::getName).collect(Collectors.joining(","));
    }

    @Recover
    public RetryException recover(Exception t){
        return new RetryException("Maximum retries reached: " + t.getMessage());
    }
}
