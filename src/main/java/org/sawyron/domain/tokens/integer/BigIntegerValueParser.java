package org.sawyron.domain.tokens.integer;

import org.sawyron.domain.filters.RegexpStringPredicate;
import org.sawyron.domain.tokens.handlers.ValueParser;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.util.function.Predicate;
import java.util.regex.Pattern;

@Component
public class BigIntegerValueParser implements ValueParser<BigInteger> {
    private final Predicate<String> tokenPredicate = new RegexpStringPredicate(Pattern.compile("^[+-]?[0-9]+$"));

    @Override
    public boolean canParse(String token) {
        return tokenPredicate.test(token);
    }

    @Override
    public BigInteger parse(String token) {
        return new BigInteger(token);
    }
}
