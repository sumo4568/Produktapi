package com.example.produktapi.controller;

import com.example.produktapi.model_product.Product;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProductControllerAllLayersIntegrationTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @LocalServerPort
    int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @Test
    void testGetAllProducts_shouldReturnGivenProducts_withTestRestTemplate() {
        // --- Act, (When) ---
        ResponseEntity<List<Product>> response = testRestTemplate.exchange(
                "/products",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                });

        List<Product> actualProducts = response.getBody().stream().toList();
        int statusCodeValue = response.getStatusCode().value();

        // --- Assert, (Then) ---
       assertProductResponse(actualProducts, statusCodeValue);
    }

    @Test
    void testGetAllProducts_shouldReturnGivenProducts_withRestAssured() {
        // --- Act, (When) ---
        Response response = given()
                .when()
                .get("/products")
                .then()
                .extract()
                .response();

        List<Product> actualProducts = response.getBody().as(List.class);
        int statusCodeValue = response.getStatusCode();

        // --- Assert, (Then) ---
        assertProductResponse(actualProducts, statusCodeValue);
    }

    private void assertProductResponse(List<Product> productList, int statusCodeValue) {
        assertNotNull(productList);
        assertEquals(HttpStatus.OK.value(), statusCodeValue, "HTTP Status code should be 200");
        assertTrue(productList.size() > 0);
    }
}
