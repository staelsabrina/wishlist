package br.com.wishlist.domain;

import java.util.HashSet;
import java.util.Set;

public class WishlistDomain {
    private final String customerId;
    private Set<WishProductDomain> wishProductsDomain = new HashSet<>();

    public WishlistDomain(String customerId){
        this.customerId = customerId;
    }

    public WishlistDomain(String customerId, Set<WishProductDomain> wishProductsDomain){
        this.customerId = customerId;
        this.wishProductsDomain = wishProductsDomain;
    }

    public String getCustomerId() {
        return customerId;
    }

    public Set<WishProductDomain> getWishProductsDomain() {
        return wishProductsDomain;
    }

    public void addProduct(WishProductDomain product) {
        wishProductsDomain.add(product);
    }

    public boolean isNotEmpty(){
        return !wishProductsDomain.isEmpty();
    }
}
