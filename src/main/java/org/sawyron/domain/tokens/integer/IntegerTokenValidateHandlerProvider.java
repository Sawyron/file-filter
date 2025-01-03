package org.sawyron.domain.tokens.integer;

import org.sawyron.domain.filters.FilterApplicationProperties;
import org.sawyron.domain.tokens.handlers.TokenValidateHandlerProviderBase;
import org.sawyron.domain.tokens.handlers.TokenValueHandler;
import org.springframework.stereotype.Component;

import java.math.BigInteger;

@Component
public class IntegerTokenValidateHandlerProvider extends TokenValidateHandlerProviderBase<BigInteger> {
    public IntegerTokenValidateHandlerProvider(
            TokenValueHandler<BigInteger> tokenValueHandler,
            FilterApplicationProperties properties
    ) {
        super(tokenValueHandler, properties);
    }

    @Override
    protected String extractOutputFileName(FilterApplicationProperties properties) {
        return properties.outputProperties().integerOutputDir();
    }
}
