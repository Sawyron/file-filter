package org.sawyron.domain.tokens.strings;

import org.sawyron.domain.filters.FilterApplicationProperties;
import org.sawyron.domain.tokens.handlers.TokenValidateHandlerProviderBase;
import org.sawyron.domain.tokens.handlers.TokenValueHandler;
import org.springframework.stereotype.Component;

@Component
public class StringTokenValidateHandlerProvider extends TokenValidateHandlerProviderBase<String> {
    public StringTokenValidateHandlerProvider(
            TokenValueHandler<String> tokenValueHandler,
            FilterApplicationProperties properties
    ) {
        super(tokenValueHandler, properties);
    }

    @Override
    protected String extractOutputFileName(FilterApplicationProperties properties) {
        return properties.outputProperties().stringOutputDir();
    }
}
