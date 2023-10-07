package br.com.wishlist.gateway.database.Impl;

import br.com.wishlist.domain.WishlistDomain;
import br.com.wishlist.exception.WishlistWasNotSaveException;
import br.com.wishlist.gateway.SaveWishlistGateway;
import br.com.wishlist.gateway.database.model.WishlistDatabase;
import br.com.wishlist.gateway.database.repository.WishlistRepository;
import br.com.wishlist.gateway.database.translator.WishlistDomainToWishlistDatabaseTranslator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class SaveWishlistGatewayImpl implements SaveWishlistGateway {

    private final WishlistRepository wishlistRepository;
    private final Logger logger = LoggerFactory.getLogger(SaveWishlistGatewayImpl.class);

    public SaveWishlistGatewayImpl(WishlistRepository wishlistRepository) {
        this.wishlistRepository = wishlistRepository;
    }

    @Override
    public void saveWishlist(WishlistDomain wishlistDomain) {
        WishlistDatabase wishlistDatabase = WishlistDomainToWishlistDatabaseTranslator.translate(wishlistDomain);
        String customerId = wishlistDomain.getCustomerId();

        try {
            wishlistRepository.save(wishlistDatabase);
        } catch (Exception e){
            logger.error("An error occurred when trying to save the wishlist to client \'{}\'", customerId);
            throw new WishlistWasNotSaveException("Wasn't possible to save the client wishlist. " + e.getMessage(), e);
        }

        logger.info("Wishlist saved with success to client \'{}\'", customerId);
    }

}
