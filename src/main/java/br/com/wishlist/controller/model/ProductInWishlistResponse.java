package br.com.wishlist.controller.model;

public class ProductInWishlistResponse {
    private String productId;
    private String productName;

    public ProductInWishlistResponse(){}
    public ProductInWishlistResponse(String productId, String productName){
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
