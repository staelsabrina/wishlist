package br.com.wishlist.usecase

import br.com.wishlist.controller.model.ProductInWishlistResponse
import br.com.wishlist.domain.WishProductDomain
import br.com.wishlist.domain.WishlistDomain
import br.com.wishlist.exception.ProductNotFoundException
import br.com.wishlist.gateway.FindWishlistByCustomerIdGateway
import spock.lang.Specification

class GetOneProductUseCaseTest extends Specification {

    FindWishlistByCustomerIdGateway findWishlistByCustomerIdGateway = Mock(FindWishlistByCustomerIdGateway)
    GetOneProductUseCase getOneProductUseCase = new GetOneProductUseCase (findWishlistByCustomerIdGateway)

    def "Must return one product if it is in client's wishlist" (){
        String customerId = "777777"
        String productId = "5555551"
        WishlistDomain wishlistDomain = getWishlistDomainWith2Products(customerId)

        when: "I call the use case"
        ProductInWishlistResponse result = getOneProductUseCase.execute(customerId, productId)

        then: "Gateway must return the wishlist to customerId"
        findWishlistByCustomerIdGateway.findWishlistByCustomerId(_) >> {
            String customerIdToGateway ->
                assert customerIdToGateway == customerId
            return wishlistDomain
        }

        and: "The use case must return the product"
        assert result.productId == productId
    }

    def "Must throw a ProductNotFoundException when product is not in the client's wishlist" (){
        String customerId = "777777"
        String productId = "5555553"
        WishlistDomain wishlistDomain = getWishlistDomainWith2Products(customerId)

        when: "I call the use case"
        ProductInWishlistResponse result = getOneProductUseCase.execute(customerId, productId)

        then: "Gateway must return the wishlist to customerId"
        findWishlistByCustomerIdGateway.findWishlistByCustomerId(_) >> {
            String customerIdToGateway ->
                assert customerIdToGateway == customerId
                return wishlistDomain
        }

        and: "The exception must be thrown"
        def e = thrown(ProductNotFoundException)
        e.message.contains("Product not found in the customer's wishlist")
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
