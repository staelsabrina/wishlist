package br.com.wishlist.controller.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class AddProductRequest {

    @NotBlank
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "ProductId must have only numbers and letters")
    private String productId;

    @NotBlank
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "ProductName must have only numbers and letters")
    private String productName;

    public AddProductRequest(String productId, String productName){
        this.productId = productId;
        this.productName = productName;
    }

    public String getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }
}
