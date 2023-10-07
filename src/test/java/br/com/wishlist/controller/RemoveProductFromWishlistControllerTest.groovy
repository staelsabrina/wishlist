package br.com.wishlist.controller

import br.com.wishlist.usecase.RemoveProductUseCase
import org.mockito.internal.matchers.Any
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import spock.lang.Specification

class RemoveProductFromWishlistControllerTest extends Specification {

    RemoveProductUseCase removeProductUseCase = Mock(RemoveProductUseCase)
    RemoveProductFromWishlistController removeProductFromWishlistController = new RemoveProductFromWishlistController(removeProductUseCase)

    def "Must return no content if the product is successfully removed" (){
        String customerId = "777777"
        String productId = "555555"

        when: "I call the controller"
        ResponseEntity<Any> result = removeProductFromWishlistController.removeProductFromWishlist(customerId, productId)

        then: "The use case must return with a result"
        1 * removeProductUseCase.execute(customerId, productId) >> {
            return true
        }

        and: "The result must have a status of 204"
        assert result.getStatusCode() == HttpStatus.NO_CONTENT
    }

    def "Must return not found if the product does not exist in the customer's wishlist" (){
        String customerId = "777777"
        String productId = "555555"

        when: "I call the controller"
        ResponseEntity<Any> result = removeProductFromWishlistController.removeProductFromWishlist(customerId, productId)

        then: "The use case must return with a result"
        1 * removeProductUseCase.execute(customerId, productId) >> {
            return false
        }

        and: "The result must have a 404 status"
        assert result.getStatusCode() == HttpStatus.NOT_FOUND
    }
}
