package br.com.wishlist.controller

import br.com.wishlist.controller.model.AddProductRequest
import br.com.wishlist.controller.model.NewProductInWishlistResponse
import br.com.wishlist.usecase.AddNewProductUseCase
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import spock.lang.Specification

class AddProductInWishlistControllerTest extends Specification {

    AddNewProductUseCase addNewProductUseCase = Mock(AddNewProductUseCase)
    AddProductInWishlistController addProductInWishlistController = new AddProductInWishlistController(addNewProductUseCase)

    def "Must receive a valid request and forward it to usecase"(){
        String customerId = "999999"
        String productId = "777777"
        String productName = "Any product"
        AddProductRequest request = new AddProductRequest(productId, productName)

        when: "I call the controller"
        ResponseEntity<NewProductInWishlistResponse> result = addProductInWishlistController.addProductInWishlist(customerId, request)

        then: "Use case should be called"
        1 * addNewProductUseCase.execute(_, _) >> {
            AddProductRequest requestToUseCase, String customerIdToUseCase ->
                assert requestToUseCase == request
                assert customerIdToUseCase == customerId
        }

        and: "The result must have status 200 and the object that was added"
        assert result.getStatusCode() == HttpStatus.OK
        assert result.body.productId == productId
        assert result.body.productName == productName
    }

}
