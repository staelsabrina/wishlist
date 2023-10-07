package br.com.wishlist.gateway;

import br.com.wishlist.domain.WishlistDomain;

public interface SaveWishlistGateway {
    void saveWishlist(WishlistDomain wishlistDomain);
}
