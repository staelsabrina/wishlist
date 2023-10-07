package br.com.wishlist.usecase.translator

import br.com.wishlist.controller.model.WishlistResponse
import br.com.wishlist.domain.WishProductDomain
import br.com.wishlist.domain.WishlistDomain
import spock.lang.Specification

class WishlistDomainToWishlistResponseTranslatorTest extends Specification {

    def "Must transform a WishlistDomain in WishlistResponse" () {
        String customerId = "777777"
        WishlistDomain wishlistDomain = getWishlistDomainWith2Products(customerId)

        when: "I call the translator"
        WishlistResponse result = WishlistDomainToWishlistResponseTranslator.translate(wishlistDomain)

        then: "Must return the object translated"
        assert result.class == WishlistResponse.class
        assert result.customerId == customerId

        def products = result.getWishProductsResponse().collect{it.productId}
        assert products.contains(wishlistDomain.getWishProductsDomain().first().getProductId())

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
