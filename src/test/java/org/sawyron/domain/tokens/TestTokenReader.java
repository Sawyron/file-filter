package org.sawyron.domain.tokens;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class TestTokenReader implements TokenReader {
    private final LinkedList<String> tokens;
    boolean closed = false;

    TestTokenReader(List<String> tokens) {
        this.tokens = new LinkedList<>(tokens);
    }

    @Override
    public Optional<String> readToken() {
        return tokens.isEmpty()
                ? Optional.empty()
                : Optional.of(tokens.removeFirst());
    }

    @Override
    public void close() {
        closed = true;
    }
}
