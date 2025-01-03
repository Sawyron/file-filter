package org.sawyron.domain.tokens.floating;

import org.sawyron.domain.filters.FilterApplicationProperties;
import org.sawyron.domain.tokens.handlers.TokenValidateHandlerProviderBase;
import org.sawyron.domain.tokens.handlers.TokenValueHandler;
import org.springframework.stereotype.Component;

@Component
public class FloatingTokenValidateHandlerProvider extends TokenValidateHandlerProviderBase<Double> {
    public FloatingTokenValidateHandlerProvider(
            TokenValueHandler<Double> tokenValueHandler,
            FilterApplicationProperties properties
    ) {
        super(tokenValueHandler, properties);
    }

    @Override
    protected String extractOutputFileName(FilterApplicationProperties properties) {
        return properties.outputProperties().floatOutputDir();
    }
}
