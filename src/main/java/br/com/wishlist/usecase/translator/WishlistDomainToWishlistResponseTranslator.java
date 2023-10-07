package br.com.wishlist.usecase.translator;

import br.com.wishlist.controller.model.WishProductResponse;
import br.com.wishlist.controller.model.WishlistResponse;
import br.com.wishlist.domain.WishProductDomain;
import br.com.wishlist.domain.WishlistDomain;

import java.util.HashSet;
import java.util.Set;

public class WishlistDomainToWishlistResponseTranslator {

    public static WishlistResponse translate(WishlistDomain wishlistDomain) {
        Set<WishProductResponse> products = new HashSet<>();

        for(WishProductDomain wishProduct: wishlistDomain.getWishProductsDomain()){
            products.add(new WishProductResponse(wishProduct.getProductId(), wishProduct.getProductName()));
        }

        return new WishlistResponse(
                wishlistDomain.getCustomerId(),
                products
        );
    }
}
