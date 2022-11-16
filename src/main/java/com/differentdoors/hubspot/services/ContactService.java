package com.differentdoors.hubspot.services;

import com.differentdoors.hubspot.models.HObject;
import com.differentdoors.hubspot.models.Objects.Contact;
import com.differentdoors.hubspot.models.Objects.Deal;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ContactService {
    @Value("${different_doors.hubspot.url}")
    private String URL;

    private final ObjectMapper objectMapper = JsonMapper.builder()
            .findAndAddModules()
            .serializationInclusion(JsonInclude.Include.NON_NULL)
            .build();

    @Autowired
    @Qualifier("Hubspot")
    private RestTemplate restTemplate;

    public HObject<Contact<String>> getContact(String id) throws JsonProcessingException {
        Map<String, String> urlParams = new HashMap<>();
        urlParams.put("path", "crm/v3/objects/contacts/" + id);

        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(URL)
                .queryParam("properties", getClassProperties());

        return objectMapper.readValue(restTemplate.getForObject(builder.buildAndExpand(urlParams).toUri(), String.class), new TypeReference<HObject<Contact<String>>>() {});
    }

    private static String getClassProperties() {
        return Arrays.stream(Contact.class.getDeclaredFields()).map(Field::getName).collect(Collectors.joining(","));
    }

}
