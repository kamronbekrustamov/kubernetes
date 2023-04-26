package com.kamronbek.posts.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kamronbek.posts.error.BadRequestException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
public class UserApiService {
    @Value("${users.url}")
    private String usersUrl;
    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final String numberOfPostsJson = "{\"numberOfPosts\":%d}";

    public void updateNumberOfPosts(Long authorId, boolean isIncrement) {
        try {
            long numberOfPosts = getNumberOfPosts(authorId);
            long newNumberOfPosts = isIncrement ? numberOfPosts + 1 : numberOfPosts - 1;
            updateNumberOfPosts(authorId, newNumberOfPosts);
        } catch(HttpClientErrorException e) {
            if (e.getStatusCode().isSameCodeAs(HttpStatus.NOT_FOUND)) {
                throw new BadRequestException(String.format("Author with id %d does not exist", authorId));
            }
            throw e;
        } catch (Exception e)  {
            throw new RuntimeException(e.getMessage());
        }
    }

    private long getNumberOfPosts(long authorId) throws JsonProcessingException {
        ResponseEntity<String> response = restTemplate.getForEntity(usersUrl + "/api/v1/users/" + authorId, String.class);
        return objectMapper.readTree(response.getBody()).get("numberOfPosts").asLong();
    }

    private void updateNumberOfPosts(long authorId, long numberOfPosts) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(String.format(numberOfPostsJson, numberOfPosts), headers);
        restTemplate.exchange(usersUrl + "/api/v1/users/" + authorId, HttpMethod.PUT, entity, String.class);
    }
}
