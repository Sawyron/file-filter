package org.sawyron.domain.tokens.floating;

public record DoubleStatistics(
        long count,
        double min,
        double max,
        double sum,
        double average
) {
}
