package org.example;

import org.example.model.User;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {

        String URL = "http://94.198.50.185:7081/api/users";
        User user = new User(3L, "James", "Brown", (byte) 53);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<User> requestBody = new HttpEntity<>(user, httpHeaders);
        ResponseEntity<String> result = restTemplate.exchange(URL, HttpMethod.GET, requestBody, String.class);

        List<String> cookies = result.getHeaders().get("set-cookie");
        httpHeaders.set("cookie", cookies.stream().collect(Collectors.joining(";")));

        String resultCode;
        result = restTemplate.exchange(URL, HttpMethod.POST, requestBody, String.class);
        resultCode = result.getBody();

        user.setName("Thomas");
        user.setLastName("Shelby");
        result = restTemplate.exchange(URL, HttpMethod.PUT, requestBody, String.class );
        resultCode += result.getBody();

        result = restTemplate.exchange(URL + "/3", HttpMethod.DELETE, requestBody, String.class );
        resultCode += result.getBody();

        System.out.println("Вывод: " + resultCode);
    }
}