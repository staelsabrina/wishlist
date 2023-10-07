package br.com.wishlist.controller;

import br.com.wishlist.commons.InputSanitizer;
import br.com.wishlist.controller.model.AddProductRequest;
import br.com.wishlist.controller.model.NewProductInWishlistResponse;
import br.com.wishlist.usecase.AddNewProductUseCase;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AddProductInWishlistController {

    private final AddNewProductUseCase addNewProductUseCase;

    public AddProductInWishlistController(AddNewProductUseCase addNewProductUseCase) {
        this.addNewProductUseCase = addNewProductUseCase;
    }


    @PostMapping("/{customerId}/products")
    public ResponseEntity<NewProductInWishlistResponse> addProductInWishlist(@PathVariable(value = "customerId") String customerId, @Valid @RequestBody AddProductRequest addProductRequest) {
        String sanitizedCustomerId = InputSanitizer.sanitize(customerId);

        addNewProductUseCase.execute(addProductRequest, sanitizedCustomerId);

        NewProductInWishlistResponse response = new NewProductInWishlistResponse(addProductRequest.getProductId(), addProductRequest.getProductName());
        return ResponseEntity.ok().body(response);
    }

}
