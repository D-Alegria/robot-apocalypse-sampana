package com.sampana.robotapocalypsesampana.util;

import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

/**
 * Created by Demilade Oladugba on 3/17/2022
 */
@Service
public class RequestManager {

    private final RestTemplate restTemplate;

    public RequestManager(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public <T> ResponseEntity<T> get(String url, HttpHeaders headers, Class<T> _type) {
        ResponseEntity<T> entity;
        try {
            if (headers == null)
                headers = new HttpHeaders();

            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<String> requestBody = new HttpEntity<>("", headers);
            entity = restTemplate.exchange(url, HttpMethod.GET, requestBody, _type);
        } catch (RestClientResponseException ex) {
            entity = new ResponseEntity<>(Utils.jsonUnMarshall(ex.getResponseBodyAsString(), _type), HttpStatus.valueOf(ex.getRawStatusCode()));
        } catch (Exception ex) {
            entity = new ResponseEntity<>(Utils.jsonUnMarshall("{}", _type), HttpStatus.GATEWAY_TIMEOUT);
        }
        return entity;
    }


}
