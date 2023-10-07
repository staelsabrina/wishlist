package br.com.wishlist.gateway.database.Impl;

import br.com.wishlist.domain.WishlistDomain;
import br.com.wishlist.gateway.FindWishlistByCustomerIdGateway;
import br.com.wishlist.gateway.database.model.WishlistDatabase;
import br.com.wishlist.gateway.database.repository.WishlistRepository;
import br.com.wishlist.gateway.database.translator.WishlistDatabaseToWishlistDomainTranslator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class FindWishlistByCustomerIdGatewayImpl implements FindWishlistByCustomerIdGateway {

    private final WishlistRepository wishlistRepository;
    private final Logger logger = LoggerFactory.getLogger(FindWishlistByCustomerIdGatewayImpl.class);

    public FindWishlistByCustomerIdGatewayImpl(WishlistRepository wishlistRepository) {
        this.wishlistRepository = wishlistRepository;
    }

    @Override
    public WishlistDomain findWishlistByCustomerId(String customerId) {
        Optional<WishlistDatabase> wishlistDatabase = wishlistRepository.findByCustomerId(customerId);
        if (wishlistDatabase.isPresent()){
            return WishlistDatabaseToWishlistDomainTranslator.translate(wishlistDatabase.get());
        }
        logger.info("Wishlist not found to customerId \'{}\'", customerId);
        return new WishlistDomain(customerId);
    }

}
