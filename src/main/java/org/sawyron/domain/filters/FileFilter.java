package org.sawyron.domain.filters;

import org.sawyron.domain.exeptions.ExceptionHandler;
import org.sawyron.domain.tokens.*;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.Optional;
import java.util.function.Supplier;

@Component
public class FileFilter {
    private static final int BATCH_SIZE = 10_000;

    private final ExceptionHandler exceptionHandler;
    private final Supplier<TokenHandler> tokenHandlerSupplier;

    public FileFilter(ExceptionHandler exceptionHandler, Supplier<TokenHandler> tokenHandlerSupplier) {
        this.exceptionHandler = exceptionHandler;
        this.tokenHandlerSupplier = tokenHandlerSupplier;
    }

    public void processFiles(Collection<Path> paths) {
        try (TokenHandler handler = tokenHandlerSupplier.get()) {
            var tokenReaderHandler = new BatchingTokenReaderHandler(
                    BATCH_SIZE,
                    tokens -> tokens.forEach(handler::handle)
            );
            TokenReader tokenReader = new SequentialTokenReader(paths.stream()
                    .map(this::createTokenReader)
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .toList());
            try (tokenReader) {
                tokenReaderHandler.handleTokenReader(tokenReader);
            }
        }
    }

    private Optional<TokenReader> createTokenReader(Path path) {
        try {
            return Optional.of(new BufferedTokenReader(Files.newBufferedReader(path)));
        } catch (IOException e) {
            exceptionHandler.handle(e);
            return Optional.empty();
        }
    }
}
