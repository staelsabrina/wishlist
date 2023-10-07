package br.com.wishlist.exception;

public class WishlistWasNotSaveException extends RuntimeException {
    public WishlistWasNotSaveException(String message, Exception exception) {
        super(message, exception);
    }
}
