package com.example.produktapi.controller;

import com.example.produktapi.model.Product;
import com.example.produktapi.service.ProductService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/*
This @WebMvcTest is a test annotation in Spring Boot that performs a focused test of a specific controller,
in this case, the ProductController. It initializes the Spring MVC infrastructure and limits the scanning
to only the specified controller class, allowing for testing of the controller's behavior in isolation.
*/
@WebMvcTest(controllers = ProductController.class)
public class ProductControllerWebLayerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    ProductService productService;

    private static final String ID_NULL_MESSAGE = "The product's id should not be null!";
    private static final String TITLE_INCORRECT_MESSAGE = "The returned product's title is incorrect!";
    private static final String PRICE_INCORRECT_MESSAGE = "The returned product's price is incorrect!";
    private static final String CATEGORY_INCORRECT_MESSAGE = "The returned product's category is incorrect!";
    private static final String DESCRIPTION_INCORRECT_MESSAGE = "The returned product's description is incorrect!";
    private static final String IMAGE_INCORRECT_MESSAGE = "The returned product's image is incorrect!";

    @Test
    void testGetAllProducts_shouldReturnGivenProducts() throws Exception {
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

        // Assigning ids to both products
        expectedProducts.get(0).setId(1);
        expectedProducts.get(1).setId(2);

        /*
        Creating a POST request builder for the "/products" endpoint. It is used to
        simulate a real POST request. It could be further customized with headers, body, etc.
        before being executed with mockMvc.perform() method in the Act section.
        */
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/products");

        /*
        The controller runs in isolation (see @WebMvcTest). That's why we are "mocking" the getAllProducts method
        in the service class (not actually calling the real one but a faked one)
        */
        when(productService.getAllProducts()).thenReturn(expectedProducts);

        // --- Act, (When) ---
        /*
        Sending a simulated GET request to "/products" endpoint. This whole test could at first sight look like
        an ordinary "unit test" but is actually an "Integration test" because we're interacting with many real
        dependencies under the hood when we perform the simulated call. That's why we're getting back a JSON String
        instead of a ResponseEntity with a List of products like we got in the unit test method. So because we're
        getting back JSON String proves that we're really interacting with a real API endpoint.
        */
        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();

        /* Retrieving the response body as a string from the MvcResult object
        and parses it to a List of Products */
        String responseBody = mvcResult.getResponse().getContentAsString(Charset.forName("UTF-8"));
        List<Product> actualProducts = new ObjectMapper().readValue(responseBody, new TypeReference<>() {
        });

        // --- Assert, (Then) ---
        for (int i = 0; i < actualProducts.size(); i++) {
            assertProduct(expectedProducts.get(i), actualProducts.get(i));
        }

        // Verifying that the mocked getAllProducts method on the ProductService class was called once
        verify(productService, times(1)
                .description("This method is only expected to be invoked once!"))
                .getAllProducts();

        // Checks that the given mocks do not have any interactions remaining to be verified.
        verifyNoMoreInteractions(productService);
    }

    private void assertProduct(Product expected, Product actual) {
        assertNotNull(actual.getId(), ID_NULL_MESSAGE);
        assertEquals(expected.getTitle(), actual.getTitle(), TITLE_INCORRECT_MESSAGE);
        assertEquals(expected.getPrice(), actual.getPrice(), PRICE_INCORRECT_MESSAGE);
        assertEquals(expected.getCategory(), actual.getCategory(), CATEGORY_INCORRECT_MESSAGE);
        assertEquals(expected.getDescription(), actual.getDescription(), DESCRIPTION_INCORRECT_MESSAGE);
        assertEquals(expected.getImage(), actual.getImage(), IMAGE_INCORRECT_MESSAGE);
    }
}
