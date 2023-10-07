package br.com.wishlist.usecase

import br.com.wishlist.controller.model.AddProductRequest
import br.com.wishlist.domain.WishProductDomain
import br.com.wishlist.domain.WishlistDomain
import br.com.wishlist.exception.MaxWishesAlreadyReachedException
import br.com.wishlist.gateway.FindWishlistByCustomerIdGateway
import br.com.wishlist.gateway.SaveWishlistGateway
import spock.lang.Specification

class AddNewProductUseCaseTest extends Specification {

    SaveWishlistGateway saveWishlistGateway = Mock(SaveWishlistGateway)
    FindWishlistByCustomerIdGateway findWishlistByCustomerIdGateway = Mock(FindWishlistByCustomerIdGateway)
    AddNewProductUseCase addNewProductUseCase = new AddNewProductUseCase(saveWishlistGateway, findWishlistByCustomerIdGateway)

    def "Must add a new product in the wishlist" () {
        AddProductRequest request = getAddProductRequest()
        String customerId = "777777"

        when: "Use case is called"
        addNewProductUseCase.execute(request, customerId)

        then: "Client's wishlist is gotten"
        findWishlistByCustomerIdGateway.findWishlistByCustomerId(_ as String) >> {
            return getWishlistDomainWith2Products(customerId)
        }

        and: "Gateway must be called"
        1 * saveWishlistGateway.saveWishlist(_) >> {
            WishlistDomain wishlist ->
                def productsIdInDomain = wishlist.getWishProductsDomain().collect{it.productId}
                assert productsIdInDomain.size() == 3
                assert "666666" in productsIdInDomain
                assert "5555551" in productsIdInDomain
                assert "5555552" in productsIdInDomain
        }
    }

    def "Must throw a MaxWishesAlreadyReachedException when try insert more than 20 products in client's wishlist" () {
        AddProductRequest request = getAddProductRequest()
        String customerId = "777777"

        when: "Use case is called"
        addNewProductUseCase.execute(request, customerId)

        then: "Client's wishlist is gotten"
        findWishlistByCustomerIdGateway.findWishlistByCustomerId(_ as String) >> {
            return getWishlistDomainWith20Products(customerId)
        }

        and: "A MaxWishesAlreadyReachedException must be thrown"
        def e = thrown(MaxWishesAlreadyReachedException)
        e.message.contains("The maximum amount of wishes has already been reached")
    }

    def "Must return without save when the product is already in client's wishlist" () {
        AddProductRequest request = getAddProductRequest()
        request.productId = "5555551"
        String customerId = "777777"

        when: "Use case is called"
        addNewProductUseCase.execute(request, customerId)

        then: "Client's wishlist is gotten"
        findWishlistByCustomerIdGateway.findWishlistByCustomerId(_ as String) >> {
            return getWishlistDomainWith2Products(customerId)
        }

        and: "Gateway must not be called"
        0 * saveWishlistGateway.saveWishlist(_)
    }

    def getAddProductRequest() {
        String productId = "666666"
        String productName = "Any new product"
        return new AddProductRequest(productId, productName)
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

    def getWishlistDomainWith20Products(String customerId){
        String productId = "555555"
        String productName = "Any product"
        Set<WishProductDomain> products = new HashSet<WishProductDomain>()

        for(int i = 0; i < 20; i++) {
            WishProductDomain product = new WishProductDomain(productId+i, productName+i)
            products.addAll(product)
        }

        return new WishlistDomain(customerId, products)
    }
}
