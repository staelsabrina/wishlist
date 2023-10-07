package br.com.wishlist.gateway;

import br.com.wishlist.domain.WishlistDomain;

public interface FindWishlistByCustomerIdGateway {
    WishlistDomain findWishlistByCustomerId(String customerId);
}
