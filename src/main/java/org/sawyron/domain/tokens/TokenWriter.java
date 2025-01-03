package org.sawyron.domain.tokens;

import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.IOException;
import java.io.UncheckedIOException;

public abstract class TokenWriter implements Closeable {
    private BufferedWriter writer;

    protected abstract BufferedWriter createWriter();

    public void write(String token) {
        if (writer == null) {
            writer = createWriter();
        }
        try {
            writer.write(token);
            writer.newLine();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    @Override
    public void close() throws IOException {
        if (writer != null) {
            writer.close();
        }
    }
}
