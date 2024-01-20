package com.example.produktapi.model_product;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

public class Product_Test {
    Product product;

    private Product createTestProduct() {
        Product product = new Product("SolGold Petite Micropave", 168.0, "jewelery", "Denna blir man glad av.", "https://fakestoreapi.com/img/61sbMiUnoGL._AC_UL640_QL65_ML3_.jpg");
        product.setId(6);
        return product;
}
//Author Sushma
    @Test
    public void testGetId() {
        product = createTestProduct();
        Assert.assertEquals("6", product.getId().toString());
    }


    //Author Sushma
    @Test
    public void testSetId() {
        product = createTestProduct();
        product.setId(32);
        Assert.assertEquals("32", product.getId().toString());
    }


    //Author Sushma
    @Test
    public void testGetTitle() {
        product = createTestProduct();
        Assert.assertEquals("SolGold Petite Micropave", product.getTitle());
}


    //Author Sushma
    @Test
    public void testSetTitle(){
    product = createTestProduct();
    product.setTitle("New test title");
    Assert.assertEquals("New test title", product.getTitle());
    }


    //Author Sushma
    @Test
    public void testGetPrice() {
        product = createTestProduct();
        Double expectedResult = 168.0;

        Double actualResult = product.getPrice();

        Assert.assertEquals(expectedResult, actualResult);
    }

    //Author Sushma
    @Test
    public void testSetPrice() {
        product = createTestProduct();
        Double newPrice = 270.3;

        product.setPrice(newPrice);
        Double actualResult = product.getPrice();

        Assert.assertEquals(newPrice, actualResult);
    }

    //Author Sushma
    @Test
    public void testGetCategory() {
        product = createTestProduct();
        Assert.assertEquals("jewelery", product.getCategory());
    }

    //Author Sushma
    @Test
    public void testSetCategory() {
        product = createTestProduct();
        product.setCategory("Test category");
        Assert.assertEquals("Test category", product.getCategory());
    }

    //Author Sushma
    @Test
    public void testGetDescription() {
        product = createTestProduct();
        Assert.assertEquals("Denna blir man glad av.", product.getDescription());
    }

    //Author Sushma
    @Test
    public void testSetDescription() {
        product = createTestProduct();
        product.setDescription("This is a new description");
        Assert.assertEquals("This is a new description", product.getDescription());
    }

    //Author Sushma
    @Test
    public void testGetImage() {
        product = createTestProduct();
        Assert.assertEquals("https://fakestoreapi.com/img/61sbMiUnoGL._AC_UL640_QL65_ML3_.jpg", product.getImage());
    }

    //Author Sushma
    @Test
    public void testSetImage() {
        product = createTestProduct();
        product.setImage("https://someurl.com/test.jpg");
        Assert.assertEquals("https://someurl.com/test.jpg", product.getImage());
    }
}