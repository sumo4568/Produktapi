package com.example.produktapi.controller;

import com.example.produktapi.model_product.Product;
import com.example.produktapi.service.ProductService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductControllerUnitTest {

    @Mock
    private ProductService productService;

    @InjectMocks
    private ProductController productController;

    @Test
    void testGetAllProducts_shouldReturnGivenProducts() {
        // --- Arrange, (Given) ---
        // Preparing the test data
        List<Product> expectedProducts = Arrays.asList(
                new Product("Fjallraven - Foldsack No. 1 Backpack, Fits 15 Laptops",
                        100.0,
                        "men's clothing",
                        "Fin väska me plats för dator",
                        "https://fakestoreapi.com/img/81fPKd-2AYL._AC_SL1500_.jpg"
                        ),
                new Product("Mens Casual Premium Slim Fit T-Shirts",
                        55.55,
                        "men's clothing",
                        "Vilken härlig t-shirt, slim fit o casual i ett!",
                        "https://fakestoreapi.com/img/71-3HjGNDUL._AC_SY879._SX._UX._SY._UY_.jpg")
        );

        /*
        Mocking the getAllProducts service method (not actually calling the
        real one but a faked one)
        */
        when(productService.getAllProducts()).thenReturn(expectedProducts);

        // --- Act, (When) ---
        // Performing the GET request
        ResponseEntity<List<Product>> response = productController.getAllProducts();

        // Saving the actual response data into variables
        HttpStatusCode actualStatusCode = response.getStatusCode();
        List<Product> actualProducts = response.getBody();

        // --- Assert, (Then) ---
        // Verifying the response data
        assertEquals(HttpStatus.OK, actualStatusCode,
                "HTTP status code 200 OK should have been returned!");
        assertTrue(actualProducts.size() == 2,
                "Two products should have been returned but got: " + actualProducts.size());

        // Verifying that the mocked getAllProducts method on the ProductService class was called once
        verify(productService, times(1)
                        .description("This method is only expected to be invoked once!"))
                        .getAllProducts();

        // Checks that the given mocks do not have any interactions remaining to be verified
        verifyNoMoreInteractions(productService);
    }
}
