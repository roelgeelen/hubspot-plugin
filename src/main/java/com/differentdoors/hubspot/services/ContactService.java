package com.differentdoors.hubspot.services;

import com.differentdoors.hubspot.models.HObject;
import com.differentdoors.hubspot.models.HResults;
import com.differentdoors.hubspot.models.Objects.AssociationV4;
import com.differentdoors.hubspot.models.Objects.Contact;
import com.differentdoors.hubspot.models.Objects.Deal;
import com.differentdoors.hubspot.models.Objects.Product;
import com.differentdoors.hubspot.models.Search.Search;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
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
import java.net.URI;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ContactService {

    private static final ObjectMapper objectMapper = JsonMapper.builder()
            .findAndAddModules()
            .serializationInclusion(JsonInclude.Include.NON_NULL)
            .build();

    private final String url;
    private final RestTemplate restTemplate;

    public ContactService(
            @Value("${different_doors.hubspot.url}") String url,
            @Qualifier("Hubspot") RestTemplate restTemplate) {
        this.url = url;
        this.restTemplate = restTemplate;
    }

    @Retryable(value = ResourceAccessException.class, maxAttempts = 3, backoff = @Backoff(delay = 1000))
    public HResults<HObject<Contact<String>>> searchContact(Search search) {
        try {
            URI uri = buildUri("crm/v3/objects/contacts/search");
            HttpEntity<String> request = jsonEntity(search);
            return objectMapper.readValue(
                    restTemplate.postForObject(uri, request, String.class),
                    new TypeReference<>() {}
            );
        } catch (Exception e) {
            throw new RuntimeException("Failed to search contacts", e);
        }
    }

    @Retryable(value = ResourceAccessException.class, maxAttempts = 3, backoff = @Backoff(delay = 1000))
    public HObject<Contact<String>> getContact(String id) {
        try {
            URI uri = buildUri("crm/v3/objects/contacts/" + id, getClassProperties());
            return objectMapper.readValue(
                    restTemplate.getForObject(uri, String.class),
                    new TypeReference<>() {}
            );
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch contact with id: " + id, e);
        }
    }

    @Retryable(value = ResourceAccessException.class, maxAttempts = 3, backoff = @Backoff(delay = 1000))
    public HObject<Contact<String>> updateContact(String id, HObject<Contact<String>> contact) {
        try {
            URI uri = buildUri("crm/v3/objects/contacts/" + id);
            HttpEntity<String> request = jsonEntity(contact);
            return objectMapper.readValue(
                    restTemplate.exchange(uri, HttpMethod.PATCH, request, String.class).getBody(),
                    new TypeReference<>() {}
            );
        } catch (Exception e) {
            throw new RuntimeException("Failed to update contact with id: " + id, e);
        }
    }

    @Retryable(value = ResourceAccessException.class, maxAttempts = 3, backoff = @Backoff(delay = 1000))
    public HResults<AssociationV4> getContactAssociation(String id, String toObjectType) {
        try {
            URI uri = buildUri("crm/v4/objects/contacts/" + id + "/associations/" + toObjectType);
            return objectMapper.readValue(
                    restTemplate.exchange(uri, HttpMethod.GET, null, String.class).getBody(),
                    new TypeReference<>() {}
            );
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch associations for contact with id: " + id, e);
        }
    }

    @Recover
    public HResults<HObject<Contact<String>>> recover(ResourceAccessException e) {
        throw new RuntimeException("Maximum retries reached: " + e.getMessage(), e);
    }

    // --- Helpers ---

    private URI buildUri(String path) {
        return UriComponentsBuilder.fromUriString(url)
                .buildAndExpand(Map.of("path", path))
                .toUri();
    }

    private URI buildUri(String path, String properties) {
        return UriComponentsBuilder.fromUriString(url)
                .queryParam("properties", properties)
                .buildAndExpand(Map.of("path", path))
                .toUri();
    }

    private HttpEntity<String> jsonEntity(Object body) throws JsonProcessingException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new HttpEntity<>(objectMapper.writeValueAsString(body), headers);
    }

    private static String getClassProperties() {
        return Arrays.stream(Contact.class.getDeclaredFields())
                .map(Field::getName)
                .collect(Collectors.joining(","));
    }
}
