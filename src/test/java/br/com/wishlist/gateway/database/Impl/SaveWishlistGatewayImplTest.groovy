package br.com.wishlist.gateway.database.Impl

import br.com.wishlist.domain.WishProductDomain
import br.com.wishlist.domain.WishlistDomain
import br.com.wishlist.exception.WishlistWasNotSaveException
import br.com.wishlist.gateway.database.model.WishlistDatabase
import br.com.wishlist.gateway.database.repository.WishlistRepository
import spock.lang.Specification

class SaveWishlistGatewayImplTest extends Specification {

    WishlistRepository wishlistRepository = Mock(WishlistRepository)
    SaveWishlistGatewayImpl saveWishlistGateway = new SaveWishlistGatewayImpl(wishlistRepository)

    def "Must save the customer's wish list" () {
        String customerId = "777777"
        WishlistDomain wishlistDomain = getWishlistDomainWith2Products(customerId)

        when: "I call the gateway"
        saveWishlistGateway.saveWishlist(wishlistDomain)

        then: "The repository must be called"
        wishlistRepository.save(_) >> {
            WishlistDatabase wishlistDatabase ->
                assert wishlistDatabase.customerId == customerId

                def productIdsInDatabase = wishlistDatabase.wishProductsDatabase.collect { it.productId }
                assert "5555551" in productIdsInDatabase
                assert "5555552" in productIdsInDatabase
        }
    }

    def "Should throw a WishlistWasNotSaveException when an exception occurs while saving the customer wishlist" () {
        String customerId = "777777"
        WishlistDomain wishlistDomain = getWishlistDomainWith2Products(customerId)

        when: "I call the gateway"
        saveWishlistGateway.saveWishlist(wishlistDomain)

        then: "The repository's save method throws any exception"
        1 * wishlistRepository.save(_) >> {
            throw new RuntimeException("An error occurs")
        }

        and: "A WishlistWasNotSaveException must be thrown"
        def e = thrown(WishlistWasNotSaveException)
        e.message.contains("Wasn't possible to save the client wishlist.")
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
