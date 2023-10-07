package br.com.wishlist.gateway.database.Impl;

import br.com.wishlist.exception.WishlistWasNotSaveException;
import br.com.wishlist.gateway.RemoveProductFromWishlistGateway;
import br.com.wishlist.gateway.database.model.WishProductDatabase;
import br.com.wishlist.gateway.database.model.WishlistDatabase;
import br.com.wishlist.gateway.database.repository.WishlistRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.Set;

@Component
public class RemoveProductFromWishlistGatewayImpl implements RemoveProductFromWishlistGateway {

    private final WishlistRepository wishlistRepository;
    private final Logger logger = LoggerFactory.getLogger(RemoveProductFromWishlistGatewayImpl.class);

    public RemoveProductFromWishlistGatewayImpl(WishlistRepository wishlistRepository) {
        this.wishlistRepository = wishlistRepository;
    }

    @Override
    public boolean removeProductFromWishlist(String customerId, String productId) {
        Optional<WishlistDatabase> wishlistDatabase = wishlistRepository.findByCustomerId(customerId);
        if(wishlistDatabase.isEmpty()){
            logger.info("Client \'{}\' doesn't have a wishlist", customerId);
            return false;
        }

        WishlistDatabase wishlist = wishlistDatabase.get();
        Set<WishProductDatabase> products = wishlist.getWishProductsDatabase();

        boolean removed = products.removeIf(product ->
            product.getProductId().equals(productId)
        );

        if (removed) {
            try {
                wishlist.setWishProductsDatabase(products);
                wishlistRepository.save(wishlist);
                logger.info("The product \'{}\' was successfully removed from customerId \'{}\' wishlist", productId, customerId);
            } catch (Exception e){
                logger.error("An error occurred when trying to save the customerId \'{}\' wishlist", customerId);
                throw new WishlistWasNotSaveException("Wasn't possible to save the client wishlist. " + e.getMessage(), e);
            }

        } else {
            logger.info("Product \'{}\' wasn't found in the customerId \'{}\' wishlist", productId, customerId);
        }

        return removed;
    }
}
