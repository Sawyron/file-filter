package org.sawyron.domain.tokens.handlers;

import org.sawyron.domain.tokens.TokenHandler;
import org.sawyron.domain.tokens.TokenValidateHandler;

import java.util.function.Predicate;

public class TokenHandlerValidateAdapter implements TokenValidateHandler {
    private final TokenHandler handler;
    private final Predicate<String> predicate;

    public TokenHandlerValidateAdapter(TokenHandler handler, Predicate<String> predicate) {
        this.handler = handler;
        this.predicate = predicate;
    }

    @Override
    public boolean canHandle(String token) {
        return predicate.test(token);
    }

    @Override
    public void handle(String token) {
        handler.handle(token);
    }

    @Override
    public void close() {
        handler.close();
    }
}
