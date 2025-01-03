package org.sawyron.domain.tokens.strings;

import org.sawyron.domain.tokens.handlers.ValueParser;
import org.springframework.stereotype.Component;

@Component
public class StringValueParser implements ValueParser<String> {
    @Override
    public boolean canParse(String token) {
        return true;
    }

    @Override
    public String parse(String token) {
        return token;
    }
}
