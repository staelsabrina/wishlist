package br.com.wishlist.gateway.database.translator;

import br.com.wishlist.domain.WishProductDomain;
import br.com.wishlist.domain.WishlistDomain;
import br.com.wishlist.gateway.database.model.WishProductDatabase;
import br.com.wishlist.gateway.database.model.WishlistDatabase;

import java.util.HashSet;
import java.util.Set;

public class WishlistDatabaseToWishlistDomainTranslator {

    public static WishlistDomain translate(WishlistDatabase wishlistDatabase){
        Set<WishProductDomain> products = new HashSet<>();

        for(WishProductDatabase wishProduct: wishlistDatabase.getWishProductsDatabase()){
            products.add(new WishProductDomain(wishProduct.getProductId(), wishProduct.getProductName()));
        }

        return new WishlistDomain(
                wishlistDatabase.getCustomerId(),
                products
        );
    }
}
