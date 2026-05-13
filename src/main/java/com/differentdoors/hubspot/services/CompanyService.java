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

    private static final ObjectMapper objectMapper = JsonMapper.builder()
            .findAndAddModules()
            .serializationInclusion(JsonInclude.Include.NON_NULL)
            .build();

    private final String url;
    private final RestTemplate restTemplate;

    public CompanyService(
            @Value("${different_doors.hubspot.url}") String url,
            @Qualifier("Hubspot") RestTemplate restTemplate) {
        this.url = url;
        this.restTemplate = restTemplate;
    }

    @Retryable(value = ResourceAccessException.class, maxAttempts = 3, backoff = @Backoff(delay = 1000))
    public HObject<Company<String>> getCompany(String id) {
        try {
            Map<String, String> urlParams = new HashMap<>();
            urlParams.put("path", "crm/v3/objects/companies/" + id);

            UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(url)
                    .queryParam("properties", getClassProperties());

            return objectMapper.readValue(
                    restTemplate.getForObject(builder.buildAndExpand(urlParams).toUri(), String.class),
                    new TypeReference<HObject<Company<String>>>() {}
            );
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch company with id: " + id, e);
        }
    }

    @Recover
    public HObject<Company<String>> recover(ResourceAccessException e, String id) {
        throw new RuntimeException("Maximum retries reached for company with id: " + id, e);
    }

    private static String getClassProperties() {
        return Arrays.stream(Company.class.getDeclaredFields())
                .map(Field::getName)
                .collect(Collectors.joining(","));
    }
}
