package br.com.wishlist.controller;

import br.com.wishlist.commons.InputSanitizer;
import br.com.wishlist.controller.model.WishlistResponse;
import br.com.wishlist.usecase.GetAllProductsUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GetAllProductsFromWishlistController {

    private final GetAllProductsUseCase getAllProductsUseCase;

    public GetAllProductsFromWishlistController(GetAllProductsUseCase getAllProductsUseCase) {
        this.getAllProductsUseCase = getAllProductsUseCase;
    }

    @GetMapping("/{customerId}/products")
    public ResponseEntity<WishlistResponse> getAllProductsInWishlist(@PathVariable(value = "customerId") String customerId) {
        String sanitizedCustomerId = InputSanitizer.sanitize(customerId);
        return ResponseEntity.ok(getAllProductsUseCase.execute(sanitizedCustomerId));
    }

}
