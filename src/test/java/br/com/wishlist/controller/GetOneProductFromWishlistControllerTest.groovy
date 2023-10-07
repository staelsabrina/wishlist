package br.com.wishlist.controller

import br.com.wishlist.controller.model.ProductInWishlistResponse
import br.com.wishlist.exception.ProductNotFoundException
import br.com.wishlist.usecase.GetOneProductUseCase
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import spock.lang.Specification

class GetOneProductFromWishlistControllerTest extends Specification {

    GetOneProductUseCase getOneProductUseCase = Mock(GetOneProductUseCase)
    GetOneProductFromWishlistController getOneProductFromWishlistController = new GetOneProductFromWishlistController(getOneProductUseCase)

    def "The requested product must be returned if it is on the customer's list" (){
        String customerId = "777777"
        String productId = "555555"
        String productName = "Any product"

        ProductInWishlistResponse returnFromUseCase = new ProductInWishlistResponse(productId, productName)

        when: "I call the controller"
        ResponseEntity<ProductInWishlistResponse> result = getOneProductFromWishlistController.findOneProductInWishlist(customerId, productId)

        then: "The use case must return with a result"
        1 * getOneProductUseCase.execute(_, _) >> {
            return returnFromUseCase;
        }

        and: "The result must have a status of 200 and the data of the product"
        assert result.getStatusCode() == HttpStatus.OK
        assert result.body.productId == productId
        assert result.body.productName == productName
    }

    def "Should catch the product not found exception and return a 404 status" (){
        String customerId = "777777"
        String productId = "555555"
        String productName = "Any product"

        ProductInWishlistResponse returnFromUseCase = new ProductInWishlistResponse(productId, productName)

        when: "I call the controller"
        ResponseEntity<ProductInWishlistResponse> result = getOneProductFromWishlistController.findOneProductInWishlist(customerId, productId)

        then: "Use should throw a product not found exception"
        1 * getOneProductUseCase.execute(_, _) >> {
            throw new ProductNotFoundException("Product not found")
        }

        and: "The result must have a 404 status and an empty body"
        assert result.getStatusCode() == HttpStatus.NOT_FOUND
        assert result.body == null
    }

}
