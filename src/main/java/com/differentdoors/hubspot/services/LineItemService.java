package com.differentdoors.hubspot.services;

import com.differentdoors.hubspot.models.HObject;
import com.differentdoors.hubspot.models.HResults;
import com.differentdoors.hubspot.models.Objects.LineItem;
import com.differentdoors.hubspot.models.Objects.Product;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;
import java.util.Map;

@Service
public class LineItemService {
    @Value("${differentdoors.hubspot.url}")
    private String URL;

    @Value("${differentdoors.hubspot.hapikey}")
    private String hapikey;


    private final ObjectMapper objectMapper = JsonMapper.builder()
            .findAndAddModules()
            .serializationInclusion(JsonInclude.Include.NON_NULL)
            .build();

    @Autowired
    private RestTemplate restTemplate;

    public HObject<LineItem> createLineItem(LineItem lineItem) throws JsonProcessingException {
        Map<String, String> urlParams = new HashMap<>();
        urlParams.put("path", "crm/v3/objects/line_items");

        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(URL)
                .queryParam("hapikey", hapikey);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Object> requestEntity = new HttpEntity<>(objectMapper.writeValueAsString(new HObject<>(lineItem)), headers);
        return objectMapper.readValue(restTemplate.postForObject(builder.buildAndExpand(urlParams).toUri(), requestEntity, String.class), new TypeReference<HObject<LineItem>>() {
        });
    }

    public void associateLineItem(String lineItemID, String toObjectType, String toObjectId, String associationType) {
        Map<String, String> urlParams = new HashMap<>();
        urlParams.put("path", "crm/v3/objects/line_items/" + lineItemID + "/associations/" + toObjectType + "/" + toObjectId + "/" + associationType);

        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(URL)
                .queryParam("hapikey", hapikey);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Object> requestEntity = new HttpEntity<>(null, headers);
        restTemplate.exchange(builder.buildAndExpand(urlParams).toUri(), HttpMethod.PUT, requestEntity, String.class);
    }
}
