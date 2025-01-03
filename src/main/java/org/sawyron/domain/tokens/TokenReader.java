package org.sawyron.domain.tokens;

import java.util.*;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public interface TokenReader extends AutoCloseable {
    Optional<String> readToken();

    @Override
    void close();

    static Stream<String> asStream(TokenReader tokenReader) {
        Iterator<String> iterator = new Iterator<>() {
            private String current;

            @Override
            public boolean hasNext() {
                if (current != null) {
                    return true;
                } else {
                    Optional<String> lineOptional = tokenReader.readToken();
                    boolean result = lineOptional.isPresent();
                    lineOptional.ifPresent(line -> current = line);
                    return result;
                }
            }

            @Override
            public String next() {
                if (current != null || hasNext()) {
                    String line = current;
                    current = null;
                    return line;
                } else {
                    throw new NoSuchElementException();
                }
            }
        };
        return StreamSupport.stream(Spliterators.spliteratorUnknownSize(
                iterator, Spliterator.ORDERED | Spliterator.NONNULL), false);
    }
}
