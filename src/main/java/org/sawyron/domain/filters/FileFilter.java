package org.sawyron.domain.filters;

import org.sawyron.domain.exeptions.ExceptionHandler;
import org.sawyron.domain.tokens.TokenBatchingReader;
import org.sawyron.domain.tokens.TokenHandler;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.function.Supplier;

@Component
public class FileFilter {
    public static final int BATCH_SIZE = 10_000;

    private final ExceptionHandler exceptionHandler;
    private final Supplier<TokenHandler> tokenHandlerSupplier;

    public FileFilter(ExceptionHandler exceptionHandler, Supplier<TokenHandler> tokenHandlerSupplier) {
        this.exceptionHandler = exceptionHandler;
        this.tokenHandlerSupplier = tokenHandlerSupplier;
    }

    public void processFiles(Collection<Path> paths) {
        try (TokenHandler handler = tokenHandlerSupplier.get()) {
            var tokenReader = new TokenBatchingReader(
                    BATCH_SIZE,
                    tokens -> tokens.forEach(handler::handle)
            );
            for (Path path : paths) {
                try (InputStream fileInputSteam = Files.newInputStream(path)) {
                    tokenReader.readTokens(fileInputSteam);
                    System.out.printf("File processed: %s%n", path);
                } catch (Exception e) {
                    exceptionHandler.handle(e);
                }
            }
        }
    }
}
