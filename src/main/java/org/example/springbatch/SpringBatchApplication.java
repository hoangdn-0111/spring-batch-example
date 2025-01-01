package org.example.springbatch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.Base64;

@SpringBootApplication
@EnableAsync
public class SpringBatchApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBatchApplication.class, args);
        System.out.println("hello world");

        String clientId = "myClientId";
        String clientSecret = "myClientSecret";
        String basicAuthHeader = generateBasicAuth(clientId, clientSecret);
        System.out.println("Basic Authentication Header: " + basicAuthHeader);
    }

    public static String generateBasicAuth(String clientId, String clientSecret) {
        String credentials = clientId + ":" + clientSecret;
        return Base64.getEncoder().encodeToString(credentials.getBytes());
    }
}
