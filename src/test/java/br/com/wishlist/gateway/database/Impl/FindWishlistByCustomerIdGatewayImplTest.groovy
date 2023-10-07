package br.com.wishlist.gateway.database.Impl


import br.com.wishlist.domain.WishlistDomain
import br.com.wishlist.gateway.database.model.WishProductDatabase
import br.com.wishlist.gateway.database.model.WishlistDatabase
import br.com.wishlist.gateway.database.repository.WishlistRepository
import spock.lang.Specification

class FindWishlistByCustomerIdGatewayImplTest extends Specification {

    WishlistRepository wishlistRepository = Mock(WishlistRepository)
    FindWishlistByCustomerIdGatewayImpl findWishlistByCustomerIdGateway = new FindWishlistByCustomerIdGatewayImpl(wishlistRepository)

    def "Must return the wishlist of a customerId" (){
        String customerId = "777777"
        WishlistDatabase wishlistDatabase = getWishlistDatabaseWith2Products(customerId)

        when: "I call the gateway"
        WishlistDomain result = findWishlistByCustomerIdGateway.findWishlistByCustomerId(customerId)

        then: "The repository must return an optional"
        1 * wishlistRepository.findByCustomerId(customerId) >> {
            return Optional.of(wishlistDatabase)
        }

        and: "The gateway must return a WishlistDomain"
        assert result.customerId == customerId
        assert result.wishProductsDomain.size() == 2

        def productIdsInDomain = result.wishProductsDomain.collect { it.productId }
        assert "5555551" in productIdsInDomain
        assert "5555552" in productIdsInDomain
    }

    def "Must return empty wishlist from a customerId with no wishlist" (){
        String customerId = "777777"

        when: "I call the gateway"
        WishlistDomain result = findWishlistByCustomerIdGateway.findWishlistByCustomerId(customerId)

        then: "The repository must return an optional"
        1 * wishlistRepository.findByCustomerId(customerId) >> {
            return Optional.empty()
        }

        and: "The gateway must return a WishlistDomain"
        assert result.customerId == customerId
        assert result.wishProductsDomain.size() == 0
    }

    def getWishlistDatabaseWith2Products(String customerId){
        String productId = "555555"
        String productName = "Any product"
        WishProductDatabase product1 = new WishProductDatabase(productId+"1", productName+"1")
        WishProductDatabase product2 = new WishProductDatabase(productId+"2", productName+"2")
        Set<WishProductDatabase> products = new HashSet<WishProductDatabase>()
        products.addAll(product1, product2)

        return new WishlistDatabase(customerId, products)
    }
}
