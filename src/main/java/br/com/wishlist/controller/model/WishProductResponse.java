package br.com.wishlist.controller.model;

public class WishProductResponse {
    private final String productId;
    private final String productName;

    public WishProductResponse(String productId, String productName) {
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
