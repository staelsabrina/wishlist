package br.com.wishlist.usecase;

import br.com.wishlist.controller.model.AddProductRequest;
import br.com.wishlist.domain.WishProductDomain;
import br.com.wishlist.domain.WishlistDomain;
import br.com.wishlist.exception.MaxWishesAlreadyReachedException;
import br.com.wishlist.gateway.FindWishlistByCustomerIdGateway;
import br.com.wishlist.gateway.SaveWishlistGateway;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class AddNewProductUseCase {

    private static final int WISH_LIST_MAX_SIZE = 20;
    private final SaveWishlistGateway saveWishlistGateway;
    private final FindWishlistByCustomerIdGateway findWishlistByCustomerIdGateway;
    private final Logger logger = LoggerFactory.getLogger(AddNewProductUseCase.class);


    public AddNewProductUseCase(SaveWishlistGateway saveWishlistGateway, FindWishlistByCustomerIdGateway findWishlistByCustomerIdGateway) {
        this.saveWishlistGateway = saveWishlistGateway;
        this.findWishlistByCustomerIdGateway = findWishlistByCustomerIdGateway;
    }

    public void execute(AddProductRequest request, String customerId){
        WishProductDomain product = new WishProductDomain(request.getProductId(), request.getProductName());

        WishlistDomain wishlist = obtainWishList(customerId);

        int wishlistSize = wishlist.getWishProductsDomain().size();

        if(wishlist.isNotEmpty() && wishlistSize >= WISH_LIST_MAX_SIZE) {
            logger.error("Customer \'{}\' already has \'{}\' products in wishlist", customerId, wishlistSize);
            throw new MaxWishesAlreadyReachedException("The maximum amount of wishes has already been reached: " + WISH_LIST_MAX_SIZE);
        }

        if(productIsAlreadyInWishlist(product, wishlist)){
            logger.info("The product \'{}\' is already in the customer \'{}\' wishlist", product.getProductName(), customerId);
            return;
        }

        wishlist.addProduct(product);

        saveWishlistGateway.saveWishlist(wishlist);
    }

    private boolean productIsAlreadyInWishlist(WishProductDomain product, WishlistDomain wishlist) {
        return wishlist.getWishProductsDomain().contains(product);
    }

    private WishlistDomain obtainWishList(String customerId) {
        return findWishlistByCustomerIdGateway.findWishlistByCustomerId(customerId);
    }

}
