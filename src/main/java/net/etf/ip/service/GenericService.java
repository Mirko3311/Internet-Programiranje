package net.etf.ip.service;

import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class GenericService<T> {

    private static final CookieManager cookieManager = new CookieManager(null, CookiePolicy.ACCEPT_ALL);
    
    private final HttpClient httpClient = HttpClient.newBuilder()
            .cookieHandler(cookieManager) 
            .build();

    private final ObjectMapper objectMapper = new ObjectMapper();
    private static final String BASE_URL = "http://localhost:8080/api";

    
    public GenericService() {
    	objectMapper.registerModule(new com.fasterxml.jackson.datatype.jsr310.JavaTimeModule());
        objectMapper.disable(com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }
    
    public <T> T get(String endpoint, Class<T> clazz) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + endpoint))
                .header("Accept", "application/json")
                .GET()
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println("SIROVI JSON SA SERVERA: " + response.body());
        if (response.statusCode() == 200) {
            return objectMapper.readValue(response.body(), clazz);
        } else {
        	System.err.println("------ DEBUG GREŠKA ------");
            System.err.println("URL: " + BASE_URL + endpoint);
            System.err.println("Statusni kod: " + response.statusCode());
            System.err.println("Poruka sa servera (Body): " + response.body());
            System.err.println("---------------------------");
            throw new RuntimeException("Greška na serveru prilikom GET zahtjeva: " + response.statusCode());
        }
    }

    public List<T> getList(String endpoint, Class<T> clazz) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + endpoint))
                .header("Accept", "application/json")
                .GET()
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            CollectionType listType = objectMapper.getTypeFactory().constructCollectionType(List.class, clazz);
            return objectMapper.readValue(response.body(), listType);
        } else {
            throw new RuntimeException("Greška na serveru: " + response.statusCode());
        }
    }

 
    public boolean delete(String endpoint) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(BASE_URL + endpoint))
                    .header("Accept", "application/json")
                    .DELETE()
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            int statusCode = response.statusCode();

            if (statusCode >= 200 && statusCode < 300) {
                System.out.println("USPJREH: Brisanje na " + endpoint + " uspjelo (Status: " + statusCode + ")");
                return true;
            } else {
                System.err.println("GREŠKA PRILIKOM BRISANJA!");
                System.err.println("Endpoint: " + endpoint);
                System.err.println("Statusni kod: " + statusCode);
                System.err.println("Odgovor servera: " + response.body());
                
                if (statusCode == 400) System.err.println("Upozorenje: Loš zahtjev (Bad Request).");
                if (statusCode == 404) System.err.println("Upozorenje: Resurs nije pronađen (Not Found).");
                if (statusCode == 500) System.err.println("Upozorenje: Interna greška na serveru (vjerovatno Foreign Key u bazi).");
                
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    
    public <R> R post(String endpoint, Object body, Class<R> clazz) throws Exception {
     
    	String jsonInput = objectMapper.writeValueAsString(body);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + endpoint))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonInput))
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200 || response.statusCode() == 201) {
            return objectMapper.readValue(response.body(), clazz);
        } else {
        	String errorDetails = response.body();
            System.err.println("[ERROR] Status: " + response.statusCode());
            System.err.println("[ERROR] Detalji sa backenda: " + errorDetails);
            throw new RuntimeException("Neuspješno slanje: " + response.statusCode());
        }
    }

    
    
    public <R> R put(String endpoint, Object body, Class<R> clazz) throws Exception {
        String jsonInput = objectMapper.writeValueAsString(body);
        System.out.println("[DEBUG] Šaljem PUT zahtjev na: " + endpoint);
        System.out.println("[DEBUG] JSON Body: " + jsonInput);


        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + endpoint))
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(jsonInput))
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() == 200 || response.statusCode() == 204) {
            if (response.body().isEmpty()) {
                return null;
            }
            return objectMapper.readValue(response.body(), clazz);
        } else {
            throw new RuntimeException("Greška pri ažuriranju: " + response.statusCode() + " - " + response.body());
        }
    }
}