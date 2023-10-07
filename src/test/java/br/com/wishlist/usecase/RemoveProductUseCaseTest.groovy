package br.com.wishlist.usecase

import br.com.wishlist.gateway.RemoveProductFromWishlistGateway
import spock.lang.Specification

class RemoveProductUseCaseTest extends Specification {

    RemoveProductFromWishlistGateway removeProductFromWishlistGateway = Mock(RemoveProductFromWishlistGateway)
    RemoveProductUseCase removeProductUseCase = new RemoveProductUseCase(removeProductFromWishlistGateway)

    def "Must send data to allow remove a product from wishlist and return true if success" () {
        String customerId = "777777"
        String productId = "5555551"

        when: "Use case is called"
        boolean result = removeProductUseCase.execute(customerId, productId)

        then: "Data must be sent to gateway and return true if success"
        removeProductFromWishlistGateway.removeProductFromWishlist(_, _) >> {
            String customerIdToGateway, String productIdToGateway ->
                assert customerIdToGateway == customerId
                assert productIdToGateway == productId
            return true
        }

        and: "Must return true if success"
        assert result == true
    }

    def "Must send data to allow remove a product from wishlist and return false if not success" () {
        String customerId = "777777"
        String productId = "5555551"

        when: "Use case is called"
        boolean result = removeProductUseCase.execute(customerId, productId)

        then: "Data must be sent to gateway and return true if success"
        removeProductFromWishlistGateway.removeProductFromWishlist(_, _) >> {
            String customerIdToGateway, String productIdToGateway ->
                assert customerIdToGateway == customerId
                assert productIdToGateway == productId
                return false
        }

        and: "Must return false if success"
        assert result == false
    }

}
