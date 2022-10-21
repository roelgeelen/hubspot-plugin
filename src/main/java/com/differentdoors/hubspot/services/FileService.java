package com.differentdoors.hubspot.services;

import com.differentdoors.hubspot.models.HObject;
import com.differentdoors.hubspot.models.HResults;
import com.differentdoors.hubspot.models.Objects.File;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;
import java.util.Map;

@Service
public class FileService {
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

    public File createFile(String folder, String filename, byte[] byteArray) throws JsonProcessingException {
        Map<String, String> urlParams = new HashMap<>();
        urlParams.put("path", "/files/v3/files");

        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(URL)
                .queryParam("hapikey", hapikey);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        MultiValueMap<String, String> fileMap = new LinkedMultiValueMap<>();
        ContentDisposition contentDisposition = ContentDisposition
                .builder("form-data")
                .name("file")
                .filename(filename)
                .build();
        fileMap.add(HttpHeaders.CONTENT_DISPOSITION, contentDisposition.toString());
        HttpEntity<byte[]> fileEntity = new HttpEntity<>(byteArray, fileMap);

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("file", fileEntity);
        body.add("folderPath", "/" + folder);
        body.add("options", "{\"access\": \"PUBLIC_INDEXABLE\", \"overwrite\":false}");

        HttpEntity<MultiValueMap<String, Object>> requestEntity =
                new HttpEntity<>(body, headers);
        try {
            return restTemplate.exchange(builder.buildAndExpand(urlParams).toUri(), HttpMethod.POST, requestEntity, File.class).getBody();
        } catch (HttpClientErrorException e) {
            return null;
        }
    }

    public HResults<File> searchFile(String filter, String filterValue) throws JsonProcessingException {
        Map<String, String> urlParams = new HashMap<>();
        urlParams.put("path", "files/v3/files/search");

        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(URL)
                .queryParam("hapikey", hapikey);

        if (filter != null) {
            builder.queryParam(filter, filterValue);
        }

        return objectMapper.readValue(restTemplate.getForObject(builder.buildAndExpand(urlParams).toUri(), String.class), new TypeReference<HResults<File>>() {});
    }

    public void deleteFile(String fileId) {
        Map<String, String> urlParams = new HashMap<>();
        urlParams.put("path", "files/v3/files/" + fileId);

        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(URL)
                .queryParam("hapikey", hapikey);

        restTemplate.delete(builder.buildAndExpand(urlParams).toUri());
    }
}
