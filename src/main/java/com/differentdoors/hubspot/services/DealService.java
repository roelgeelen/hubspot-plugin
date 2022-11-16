package com.differentdoors.hubspot.services;

import com.differentdoors.hubspot.models.HResults;
import com.differentdoors.hubspot.models.Objects.Association;
import com.differentdoors.hubspot.models.Objects.Deal;
import com.differentdoors.hubspot.models.HObject;
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
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
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

    public HObject<Deal<String>> getDeal(String id) throws JsonProcessingException {
        Map<String, String> urlParams = new HashMap<>();
        urlParams.put("path", "crm/v3/objects/deals/" + id);

        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(URL)
                .queryParam("properties", getClassProperties());

        return objectMapper.readValue(restTemplate.getForObject(builder.buildAndExpand(urlParams).toUri(), String.class), new TypeReference<HObject<Deal<String>>>() {});
    }

    public void updateDeal(String id, HObject<Deal<String>> deal) throws JsonProcessingException {
        Map<String, String> urlParams = new HashMap<>();
        urlParams.put("path", "crm/v3/objects/deals/" + id);

        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(URL);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Object> requestEntity = new HttpEntity<>(objectMapper.writeValueAsString(deal), headers);
        restTemplate.exchange(builder.buildAndExpand(urlParams).toUri(), HttpMethod.PATCH, requestEntity, String.class);
    }

    public HResults<Association> getDealAssociation(String id, String toObjectType) throws JsonProcessingException {
        Map<String, String> urlParams = new HashMap<>();
        urlParams.put("path", "crm/v3/objects/deals/" + id + "/associations/" + toObjectType);

        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(URL);

        return objectMapper.readValue(restTemplate.getForObject(builder.buildAndExpand(urlParams).toUri(), String.class), new TypeReference<HResults<Association>>() {});
    }

    public void deleteDealAssociation(String id, String toObjectType, String toObjectId, String associationType) {
        Map<String, String> urlParams = new HashMap<>();
        urlParams.put("path", "crm/v3/objects/deals/" + id + "/associations/" + toObjectType + "/" + toObjectId + "/" + associationType);

        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(URL);

        restTemplate.delete(builder.buildAndExpand(urlParams).toUri());
    }

    private static String getClassProperties() {
        return Arrays.stream(Deal.class.getDeclaredFields()).map(Field::getName).collect(Collectors.joining(","));
    }

}
