package org.sawyron.domain.tokens.integer;

import java.math.BigDecimal;
import java.math.BigInteger;

public record IntegerStatistics(
        long count,
        BigInteger min,
        BigInteger max,
        BigInteger sum,
        BigDecimal average
) {
}
