package br.com.wishlist.controller.model;

public class NewProductInWishlistResponse {
    private String productId;
    private String productName;

    public NewProductInWishlistResponse(String productId, String productName){
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
