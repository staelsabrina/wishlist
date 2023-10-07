package br.com.wishlist.usecase;

import br.com.wishlist.controller.model.ProductInWishlistResponse;
import br.com.wishlist.domain.WishProductDomain;
import br.com.wishlist.domain.WishlistDomain;
import br.com.wishlist.exception.ProductNotFoundException;
import br.com.wishlist.gateway.FindWishlistByCustomerIdGateway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class GetOneProductUseCase {

    private final FindWishlistByCustomerIdGateway findWishlistByCustomerIdGateway;
    private final Logger logger = LoggerFactory.getLogger(GetOneProductUseCase.class);

    public GetOneProductUseCase(FindWishlistByCustomerIdGateway findWishlistByCustomerIdGateway) {
        this.findWishlistByCustomerIdGateway = findWishlistByCustomerIdGateway;
    }

    public ProductInWishlistResponse execute(String customerId, String productId){
        WishlistDomain domain = findWishlistByCustomerIdGateway.findWishlistByCustomerId(customerId);
        Set<WishProductDomain> productsDomain = domain.getWishProductsDomain();

        WishProductDomain foundProduct = productsDomain.stream()
                .filter(product -> productId.equals(product.getProductId()))
                .findFirst()
                .orElse(null);

        if (foundProduct != null){
            logger.info("The product with id \'{}\' was found in the wishlist. customerId: \'{}\'", productId, customerId);
            return new ProductInWishlistResponse(foundProduct.getProductId(), foundProduct.getProductName());
        } else {
            logger.info("The product with id \'{}\' wasn't found in the wishlist. customerId: \'{}\'", productId, customerId);
            throw new ProductNotFoundException("Product not found in the customer's wishlist " + customerId);
        }
    }
}
