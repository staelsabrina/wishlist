package br.com.wishlist.usecase;

import br.com.wishlist.controller.model.WishlistResponse;
import br.com.wishlist.domain.WishlistDomain;
import br.com.wishlist.gateway.FindWishlistByCustomerIdGateway;
import br.com.wishlist.usecase.translator.WishlistDomainToWishlistResponseTranslator;
import org.springframework.stereotype.Component;

@Component
public class GetAllProductsUseCase {

    private final FindWishlistByCustomerIdGateway findWishlistByCustomerIdGateway;

    public GetAllProductsUseCase(FindWishlistByCustomerIdGateway findWishlistByCustomerIdGateway) {
        this.findWishlistByCustomerIdGateway = findWishlistByCustomerIdGateway;
    }

    public WishlistResponse execute(String customerId) {
        WishlistDomain domain = findWishlistByCustomerIdGateway.findWishlistByCustomerId(customerId);
        return WishlistDomainToWishlistResponseTranslator.translate(domain);
    }
}
