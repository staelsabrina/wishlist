package br.com.wishlist.gateway;

public interface RemoveProductFromWishlistGateway {
    boolean removeProductFromWishlist(String customerId, String productId);
}
