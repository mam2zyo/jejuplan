package net.codecraft.jejutrip.security.exception;

public class TokenForgeryException extends RuntimeException{

    private static final long serialVersionUID = 1L;

    public TokenForgeryException(String message) {
        super(message);
    }
}