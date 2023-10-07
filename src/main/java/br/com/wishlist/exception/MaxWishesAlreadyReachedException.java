package br.com.wishlist.exception;

public class MaxWishesAlreadyReachedException extends RuntimeException {
    public MaxWishesAlreadyReachedException(String message) {
        super(message);
    }
}
