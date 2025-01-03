package org.sawyron.domain.tokens.handlers;

import org.sawyron.domain.filters.FilterApplicationProperties;
import org.sawyron.domain.tokens.TokenFileWriter;
import org.sawyron.domain.tokens.TokenHandler;
import org.sawyron.domain.tokens.TokenValidateHandler;
import org.sawyron.domain.tokens.TokenValidateHandlerProvider;

import java.nio.file.Path;
import java.util.List;

public abstract class TokenValidateHandlerProviderBase<T> implements TokenValidateHandlerProvider {
    private final TokenValueHandler<T> tokenValueHandler;
    private final FilterApplicationProperties properties;

    protected TokenValidateHandlerProviderBase(
            TokenValueHandler<T> tokenValueHandler,
            FilterApplicationProperties properties
    ) {
        this.tokenValueHandler = tokenValueHandler;
        this.properties = properties;
    }

    protected abstract String extractOutputFileName(FilterApplicationProperties properties);

    @Override
    public TokenValidateHandler provide() {
        var writer = new TokenFileWriter(
                properties.outputDir().resolve(
                        Path.of(properties.prefix() + extractOutputFileName(properties) + ".txt")
                ),
                properties.isAppend()
        );
        TokenHandler filter = new SequenceTokenHandler(List.of(tokenValueHandler, new WriterTokenHandler(writer)));
        return new TokenHandlerValidateAdapter(filter, tokenValueHandler::canHandle);
    }
}
