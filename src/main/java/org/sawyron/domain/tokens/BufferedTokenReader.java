package org.sawyron.domain.tokens;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.Optional;

public class BufferedTokenReader implements TokenReader {
    private final BufferedReader reader;

    public BufferedTokenReader(BufferedReader reader) {
        this.reader = reader;
    }

    @Override
    public Optional<String> readToken() {
        try {
            return Optional.ofNullable(reader.readLine());
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    @Override
    public void close() {
        try {
            reader.close();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
