package br.com.wishlist.gateway.database.translator


import br.com.wishlist.domain.WishlistDomain
import br.com.wishlist.gateway.database.model.WishProductDatabase
import br.com.wishlist.gateway.database.model.WishlistDatabase
import spock.lang.Specification

class WishlistDatabaseToWishlistDomainTranslatorTest extends Specification {

    def "Must transform a WishlistDatabase in WishlistDomain" () {
        String customerId = "777777"
        WishlistDatabase wishlistDatabase = getWishlistDatabaseWith2Products(customerId)

        when: "I call the translator"
        WishlistDomain result = WishlistDatabaseToWishlistDomainTranslator.translate(wishlistDatabase)

        then: "Must return the object translated"
        assert result.class == WishlistDomain.class
        assert result.customerId == customerId

        def products = result.wishProductsDomain.collect{it.productId}
        assert products.contains(wishlistDatabase.wishProductsDatabase.first().getProductId())

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
