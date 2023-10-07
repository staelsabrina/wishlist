package br.com.wishlist.gateway.database.Impl

import br.com.wishlist.exception.WishlistWasNotSaveException
import br.com.wishlist.gateway.database.model.WishProductDatabase
import br.com.wishlist.gateway.database.model.WishlistDatabase
import br.com.wishlist.gateway.database.repository.WishlistRepository
import spock.lang.Specification

class RemoveProductFromWishlistGatewayImplTest extends Specification {

    WishlistRepository wishlistRepository = Mock(WishlistRepository)
    RemoveProductFromWishlistGatewayImpl removeProductFromWishlistGateway = new RemoveProductFromWishlistGatewayImpl(wishlistRepository)

    def "Must remove a product from the customer's wish list and return true" (){
        String customerId = "777777"
        String productId = "5555551"
        WishlistDatabase wishlistDatabase = getWishlistDatabaseWith2Products(customerId)

        when: "I call the gateway"
        boolean result = removeProductFromWishlistGateway.removeProductFromWishlist(customerId, productId)

        then: "The repository must return an optional"
        1 * wishlistRepository.findByCustomerId(customerId) >> {
            return Optional.of(wishlistDatabase)
        }

        then: "The wishlist without the product must be saved"
        1 * wishlistRepository.save(_) >> {
            WishlistDatabase wishListWithoutProduct ->
                def products = wishListWithoutProduct.wishProductsDatabase.collect{it.productId}
                assert !products.contains(productId)
        }

        and: "The return must be true"
        assert result == true
    }

    def "Must return false when the repository returns an empty optional" (){
        String customerId = "777777"
        String productId = "5555551"

        when: "I call the gateway"
        boolean result = removeProductFromWishlistGateway.removeProductFromWishlist(customerId, productId)

        then: "The repository must return an empty optional"
        1 * wishlistRepository.findByCustomerId(customerId) >> {
            return Optional.empty()
        }

        then: "The repository's save method should not be called"
        0 * wishlistRepository.save(_)

        and: "The return must be false"
        assert result == false
    }

    def "Must return false when the product is not found in the client's wishlist" (){
        String customerId = "777777"
        String productId = "444444"
        WishlistDatabase wishlistDatabase = getWishlistDatabaseWith2Products(customerId)

        when: "I call the gateway"
        boolean result = removeProductFromWishlistGateway.removeProductFromWishlist(customerId, productId)

        then: "The repository must return an optional"
        1 * wishlistRepository.findByCustomerId(customerId) >> {
            return Optional.of(wishlistDatabase)
        }

        then: "The repository's save method should not be called"
        0 * wishlistRepository.save(_)

        and: "The return must be false"
        assert result == false
    }

    def "It should throw an exception stating that it was not possible to save the list without the product when an error occurs when saving" (){
        String customerId = "777777"
        String productId = "5555551"
        WishlistDatabase wishlistDatabase = getWishlistDatabaseWith2Products(customerId)

        when: "I call the gateway"
        boolean result = removeProductFromWishlistGateway.removeProductFromWishlist(customerId, productId)

        then: "The repository must return an optional"
        1 * wishlistRepository.findByCustomerId(customerId) >> {
            return Optional.of(wishlistDatabase)
        }

        then: "The repository's save method throws any exception"
        1 * wishlistRepository.save(_) >> {
            throw new RuntimeException("An error occurs")
        }

        and: "A WishlistWasNotSaveException must be thrown"
        def e = thrown(WishlistWasNotSaveException)
        e.message.contains("Wasn't possible to save the client wishlist.")
    }


    def getWishlistDatabaseWith2Products(String customerId){
        String productId = "555555"
        String productName = "any product"
        WishProductDatabase product1 = new WishProductDatabase(productId+"1", productName+"1")
        WishProductDatabase product2 = new WishProductDatabase(productId+"2", productName+"2")
        Set<WishProductDatabase> products = new HashSet<WishProductDatabase>()
        products.addAll(product1, product2)

        return new WishlistDatabase(customerId, products)
    }

}
