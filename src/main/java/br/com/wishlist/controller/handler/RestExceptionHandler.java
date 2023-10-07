package br.com.wishlist.controller.handler;

import br.com.wishlist.exception.MaxWishesAlreadyReachedException;
import br.com.wishlist.exception.ProductNotFoundException;
import br.com.wishlist.exception.WishlistWasNotSaveException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler({IllegalArgumentException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleArgumentNotValidException(final IllegalArgumentException e) {
        String message = e.getMessage();
        return new ErrorResponse(message);
    }

    @ExceptionHandler({MaxWishesAlreadyReachedException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleMaxWishesAlreadyReachedException(final MaxWishesAlreadyReachedException e) {
        String message = e.getMessage();
        return new ErrorResponse(message);
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleMethodArgumentNotValidException(final MethodArgumentNotValidException ex) {
        final ErrorResponse errorResponse = new ErrorResponse("Argument is not valid");
        ex.getBindingResult().getFieldErrors().forEach(fieldError -> errorResponse.addError(fieldError.getField(), fieldError.getDefaultMessage()));
        return errorResponse;
    }

    @ExceptionHandler({ProductNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleProductNotFoundException(final ProductNotFoundException ex) {
        String message = ex.getMessage();
        return new ErrorResponse(message);
    }

    @ExceptionHandler({WishlistWasNotSaveException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleWishlistWasNotSaveException(final WishlistWasNotSaveException ex) {
        String message = ex.getMessage();
        return new ErrorResponse(message);
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleGlobalException(Exception ex) {
        return new ErrorResponse("Unexpected error");
    }
}
