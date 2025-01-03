package org.sawyron.domain.tokens;

public interface TokenHandler extends AutoCloseable {
    void handle(String token);

    default void close() {
    }
}
