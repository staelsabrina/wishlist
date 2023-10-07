package br.com.wishlist.usecase

import br.com.wishlist.controller.model.WishlistResponse
import br.com.wishlist.domain.WishProductDomain
import br.com.wishlist.domain.WishlistDomain
import br.com.wishlist.gateway.FindWishlistByCustomerIdGateway
import spock.lang.Specification

class GetAllProductsUseCaseTest extends Specification {

    FindWishlistByCustomerIdGateway findWishlistByCustomerIdGateway = Mock(FindWishlistByCustomerIdGateway)
    GetAllProductsUseCase getAllProductsUseCase = new GetAllProductsUseCase(findWishlistByCustomerIdGateway)

    def "Must return a WishlistResponse, getting by customerId" () {
        String customerId = "777777"
        WishlistDomain wishlistDomain = getWishlistDomainWith2Products(customerId)

        when: "I call use case"
        WishlistResponse result = getAllProductsUseCase.execute(customerId)

        then: "Gateway must return the WishlistDomain"
        1 * findWishlistByCustomerIdGateway.findWishlistByCustomerId(_) >> {
            String customerIdToGateway ->
                assert customerIdToGateway == customerId
            return wishlistDomain
        }

        and: "Return must be wishlistDomain translated to WishlistResponse"
        assert result.class == WishlistResponse.class
        assert result.customerId == customerId
        assert result.getWishProductsResponse().size() == 2

        def productsInResponse = result.getWishProductsResponse().collect{it.productId}
        assert "5555551" in productsInResponse
        assert "5555552" in productsInResponse
    }

    def getWishlistDomainWith2Products(String customerId){
        String productId = "555555"
        String productName = "Any product"
        WishProductDomain product1 = new WishProductDomain(productId+"1", productName+"1")
        WishProductDomain product2 = new WishProductDomain(productId+"2", productName+"2")
        Set<WishProductDomain> products = new HashSet<WishProductDomain>()
        products.addAll(product1, product2)

        return new WishlistDomain(customerId, products)
    }
}
