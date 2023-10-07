package br.com.wishlist.controller

import br.com.wishlist.controller.model.WishProductResponse
import br.com.wishlist.controller.model.WishlistResponse
import br.com.wishlist.usecase.GetAllProductsUseCase
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import spock.lang.Specification

class GetAllProductsFromWishlistControllerTest extends Specification {

    GetAllProductsUseCase getAllProductsUseCase = Mock(GetAllProductsUseCase)
    GetAllProductsFromWishlistController getAllProductsFromWishlistController = new GetAllProductsFromWishlistController(getAllProductsUseCase)

    def "Must respond with all products on a customer's list"(){
        String customerId = "777777"
        WishlistResponse response = getWishlistResponseWith2Products(customerId)

        when: "I call the controller"
        ResponseEntity<WishlistResponse> result = getAllProductsFromWishlistController.getAllProductsInWishlist(customerId)

        then: "Use case should be called"
        1 * getAllProductsUseCase.execute(_) >> {
            String customerIdUseCase ->
                assert customerIdUseCase == customerId
            return response
        }

        and: "The result must have a status of 200 and the client's complete wishlist"
        assert result.getStatusCode() == HttpStatus.OK
        assert result.body.customerId == customerId
        assert result.body.wishProductsResponse.size() == 2

        def productIdsInResponse = result.body.wishProductsResponse.collect { it.productId }
        assert "5555551" in productIdsInResponse
        assert "5555552" in productIdsInResponse
    }

    def getWishlistResponseWith2Products(String customerId){
        String productId = "555555"
        String productName = "Any product"
        WishProductResponse product1 = new WishProductResponse(productId+"1", productName+"1")
        WishProductResponse product2 = new WishProductResponse(productId+"2", productName+"2")
        Set<WishProductResponse> products = new HashSet<WishProductResponse>()
        products.addAll(product1, product2)

        return new WishlistResponse(customerId, products)
    }
}
