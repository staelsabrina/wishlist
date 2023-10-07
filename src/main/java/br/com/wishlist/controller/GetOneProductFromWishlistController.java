package br.com.wishlist.controller;

import br.com.wishlist.commons.InputSanitizer;
import br.com.wishlist.controller.model.ProductInWishlistResponse;
import br.com.wishlist.exception.ProductNotFoundException;
import br.com.wishlist.usecase.GetOneProductUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GetOneProductFromWishlistController {

    private final GetOneProductUseCase getOneProductUseCase;

    public GetOneProductFromWishlistController(GetOneProductUseCase getOneProductUseCase) {
        this.getOneProductUseCase = getOneProductUseCase;
    }

    @GetMapping("/{customerId}/products/{productId}")
    public ResponseEntity<ProductInWishlistResponse> findOneProductInWishlist(@PathVariable(value = "customerId") String customerId, @PathVariable(value = "productId") String productId) {
        String sanitizedCustomerId = InputSanitizer.sanitize(customerId);
        String sanitizedProductId = InputSanitizer.sanitize(productId);

        try {
            ProductInWishlistResponse result = getOneProductUseCase.execute(sanitizedCustomerId, sanitizedProductId);
            return ResponseEntity.ok(result);
        } catch (ProductNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

}
