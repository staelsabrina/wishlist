package br.com.wishlist.gateway.database.translator

import br.com.wishlist.domain.WishProductDomain
import br.com.wishlist.domain.WishlistDomain
import br.com.wishlist.gateway.database.model.WishlistDatabase
import spock.lang.Specification

class WishlistDomainToWishlistDatabaseTranslatorTest extends Specification {

    def "Must transform a WishlistDomain in WishlistDatabase" () {
        String customerId = "777777"
        WishlistDomain wishlistDomain = getWishlistDomainWith2Products(customerId)

        when: "I call the translator"
        WishlistDatabase result = WishlistDomainToWishlistDatabaseTranslator.translate(wishlistDomain)

        then: "Must return the object translated"
        assert result.class == WishlistDatabase.class
        assert result.customerId == customerId

        def products = result.wishProductsDatabase.collect{it.productId}
        assert products.contains(wishlistDomain.getWishProductsDomain().first().productId)

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
