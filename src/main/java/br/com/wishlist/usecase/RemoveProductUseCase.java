package br.com.wishlist.usecase;

import br.com.wishlist.gateway.RemoveProductFromWishlistGateway;
import org.springframework.stereotype.Component;

@Component
public class RemoveProductUseCase {

    private final RemoveProductFromWishlistGateway removeProductFromWishlistGateway;

    public RemoveProductUseCase(RemoveProductFromWishlistGateway removeProductFromWishlistGateway) {
        this.removeProductFromWishlistGateway = removeProductFromWishlistGateway;
    }

    public boolean execute(String customerId, String productId) {
        return removeProductFromWishlistGateway.removeProductFromWishlist(customerId, productId);
    }
}
