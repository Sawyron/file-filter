package org.sawyron.domain.tokens;

import java.io.*;
import java.nio.file.Path;

public class TokenFileWriter extends TokenWriter {
    private final Path path;
    private final boolean isAppending;

    public TokenFileWriter(Path path, boolean isAppending) {
        this.path = path;
        this.isAppending = isAppending;
    }

    @Override
    protected BufferedWriter createWriter() {
        try {
            return new BufferedWriter(
                    new OutputStreamWriter(
                            new FileOutputStream(path.toFile(), isAppending)
                    )
            );
        } catch (FileNotFoundException e) {
            throw new UncheckedIOException(e);
        }
    }
}
