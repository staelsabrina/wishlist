package br.com.wishlist.gateway.database.translator;

import br.com.wishlist.domain.WishProductDomain;
import br.com.wishlist.domain.WishlistDomain;
import br.com.wishlist.gateway.database.model.WishProductDatabase;
import br.com.wishlist.gateway.database.model.WishlistDatabase;

import java.util.HashSet;
import java.util.Set;

public class WishlistDomainToWishlistDatabaseTranslator {

    public static WishlistDatabase translate(WishlistDomain wishlistDomain){
        Set<WishProductDatabase> products = new HashSet<>();

        for(WishProductDomain wishProduct: wishlistDomain.getWishProductsDomain()){
            products.add(new WishProductDatabase(wishProduct.getProductId(), wishProduct.getProductName()));
        }

        return new WishlistDatabase(
                wishlistDomain.getCustomerId(),
                products
        );
    }
}
