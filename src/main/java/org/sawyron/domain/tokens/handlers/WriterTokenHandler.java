package org.sawyron.domain.tokens.handlers;

import org.sawyron.domain.tokens.TokenHandler;
import org.sawyron.domain.tokens.TokenWriter;

import java.io.IOException;

public class WriterTokenHandler implements TokenHandler {
    private final TokenWriter tokenWriter;

    public WriterTokenHandler(TokenWriter tokenWriter) {
        this.tokenWriter = tokenWriter;
    }

    @Override
    public void handle(String token) {
        tokenWriter.write(token);
    }

    @Override
    public void close() {
        try {
            tokenWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
