package br.com.wishlist.gateway.database.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashSet;
import java.util.Set;

@Document(collection = "wishlist")
public class WishlistDatabase {
    @Id
    private String customerId;
    private Set<WishProductDatabase> wishProductsDatabase = new HashSet<>();

    public WishlistDatabase() {}

    public WishlistDatabase(String customerId, Set<WishProductDatabase> wishProductsDatabase) {
        this.customerId = customerId;
        this.wishProductsDatabase = wishProductsDatabase;
    }

    public String getCustomerId() {
        return customerId;
    }

    public Set<WishProductDatabase> getWishProductsDatabase() {
        return wishProductsDatabase;
    }

    public void setWishProductsDatabase(Set<WishProductDatabase> wishProductsDatabase) {
        this.wishProductsDatabase = wishProductsDatabase;
    }
}
