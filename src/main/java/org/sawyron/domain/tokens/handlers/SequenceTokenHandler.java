package org.sawyron.domain.tokens.handlers;

import org.sawyron.domain.tokens.TokenHandler;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class SequenceTokenHandler implements TokenHandler {
    private final List<TokenHandler> tokenHandlers;

    public SequenceTokenHandler(Collection<? extends TokenHandler> tokenFilters) {
        this.tokenHandlers = new ArrayList<>(tokenFilters);
    }

    @Override
    public void handle(String token) {
        for (TokenHandler tokenHandler : tokenHandlers) {
            tokenHandler.handle(token);
        }
    }

    @Override
    public void close() {
        var exceptions = new LinkedList<Exception>();
        for (TokenHandler tokenHandler : tokenHandlers) {
            try {
                tokenHandler.close();
            } catch (Exception e) {
                exceptions.add(e);
            }
        }
        if (!exceptions.isEmpty()) {
            throw new RuntimeException(exceptions.getLast());
        }
    }
}
