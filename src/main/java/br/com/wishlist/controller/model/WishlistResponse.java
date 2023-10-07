package br.com.wishlist.controller.model;

import java.util.HashSet;
import java.util.Set;

public class WishlistResponse {
    private final String customerId;
    private Set<WishProductResponse> wishProductsResponse = new HashSet<>();

    public WishlistResponse(String customerId, Set<WishProductResponse> wishProductsResponse) {
        this.customerId = customerId;
        this.wishProductsResponse = wishProductsResponse;
    }

    public String getCustomerId() {
        return customerId;
    }

    public Set<WishProductResponse> getWishProductsResponse() {
        return wishProductsResponse;
    }
}
