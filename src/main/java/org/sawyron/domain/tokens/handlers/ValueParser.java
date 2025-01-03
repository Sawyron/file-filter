package org.sawyron.domain.tokens.handlers;

public interface ValueParser<T> {
    boolean canParse(String token);

    T parse(String token);
}
