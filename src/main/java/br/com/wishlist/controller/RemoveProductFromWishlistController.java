package br.com.wishlist.controller;

import br.com.wishlist.commons.InputSanitizer;
import br.com.wishlist.usecase.RemoveProductUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RemoveProductFromWishlistController {

    private final RemoveProductUseCase removeProductUseCase;

    public RemoveProductFromWishlistController(RemoveProductUseCase removeProductUseCase) {
        this.removeProductUseCase = removeProductUseCase;
    }

    @DeleteMapping("/{customerId}/products/{productId}")
    public ResponseEntity<Void> removeProductFromWishlist(@PathVariable(value = "customerId") String customerId, @PathVariable(value = "productId") String productId) {
        String sanitizedCustomerId = InputSanitizer.sanitize(customerId);
        String sanitizedProductId = InputSanitizer.sanitize(productId);

        boolean result = removeProductUseCase.execute(sanitizedCustomerId, sanitizedProductId);
        if (result){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

}
