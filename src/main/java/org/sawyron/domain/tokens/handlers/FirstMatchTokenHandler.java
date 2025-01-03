package org.sawyron.domain.tokens.handlers;

import org.sawyron.domain.tokens.TokenValidateHandler;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class FirstMatchTokenHandler implements TokenValidateHandler {
    private final List<TokenValidateHandler> tokenHandlers;

    public FirstMatchTokenHandler(Collection<? extends TokenValidateHandler> tokenHandlers) {
        this.tokenHandlers = new ArrayList<>(tokenHandlers);
    }

    @Override
    public boolean canHandle(String token) {
        return tokenHandlers.stream()
                .anyMatch(handler -> handler.canHandle(token));
    }

    @Override
    public void handle(String token) {
        for (TokenValidateHandler tokenHandler : tokenHandlers) {
            if (tokenHandler.canHandle(token)) {
                tokenHandler.handle(token);
                break;
            }
        }
    }

    @Override
    public void close() {
        var exceptions = new LinkedList<Exception>();
        for (TokenValidateHandler tokenHandler : tokenHandlers) {
            try {
                tokenHandler.close();
            } catch (Exception e) {
                exceptions.add(e);
            }
        }
        if (!exceptions.isEmpty()) {
            throw new RuntimeException(exceptions.getFirst());
        }
    }
}
