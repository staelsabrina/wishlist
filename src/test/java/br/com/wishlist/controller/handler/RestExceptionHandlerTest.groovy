package br.com.wishlist.controller.handler

import br.com.wishlist.exception.MaxWishesAlreadyReachedException
import br.com.wishlist.exception.ProductNotFoundException
import br.com.wishlist.exception.WishlistWasNotSaveException
import org.springframework.validation.BeanPropertyBindingResult
import org.springframework.validation.FieldError
import org.springframework.web.bind.MethodArgumentNotValidException
import spock.lang.Specification

class RestExceptionHandlerSpec extends Specification {

    def "handle ArgumentNotValidException should return ErrorResponse"() {
        given:
        def exception = new IllegalArgumentException("Invalid argument")
        def handler = new RestExceptionHandler()

        when:
        def response = handler.handleArgumentNotValidException(exception)

        then:
        response.errorMessage == "Invalid argument"
    }

    def "handle MaxWishesAlreadyReachedException should return ErrorResponse"() {
        given:
        def exception = new MaxWishesAlreadyReachedException("Max wishes reached")
        def handler = new RestExceptionHandler()

        when:
        def response = handler.handleMaxWishesAlreadyReachedException(exception)

        then:
        response.errorMessage == "Max wishes reached"
    }

    def "handle ProductNotFoundException should return ErrorResponse"() {
        given:
        def exception = new ProductNotFoundException("Product not found")
        def handler = new RestExceptionHandler()

        when:
        def response = handler.handleProductNotFoundException(exception)

        then:
        response.errorMessage == "Product not found"
    }

    def "handle WishlistWasNotSaveException should return ErrorResponse"() {
        given:
        def exception = new WishlistWasNotSaveException("Wishlist is not save", new Exception())
        def handler = new RestExceptionHandler()

        when:
        def response = handler.handleWishlistWasNotSaveException(exception)

        then:
        response.errorMessage == "Wishlist is not save"
    }

    def "handle MethodArgumentNotValidException should return ErrorResponse"() {
        given:
        def fieldError = new FieldError("objectName", "fieldName", "Invalid field")
        def bindingResult = new BeanPropertyBindingResult(new Object(), "objectName")
        bindingResult.addError(fieldError)
        def exception = new MethodArgumentNotValidException(null, bindingResult)
        def handler = new RestExceptionHandler()

        when:
        def response = handler.handleMethodArgumentNotValidException(exception)

        then:
        response.errorMessage == "Argument is not valid"
        response.errorDetails.size() == 1
        response.errorDetails[0].field == "fieldName"
        response.errorDetails[0].message == "Invalid field"
    }

    def "handle Exception should return ErrorResponse"() {
        given:
        def exception = new Exception("A generic exception occurs")
        def handler = new RestExceptionHandler()

        when:
        def response = handler.handleGlobalException(exception)

        then:
        response.errorMessage == "Unexpected error"
    }

}
