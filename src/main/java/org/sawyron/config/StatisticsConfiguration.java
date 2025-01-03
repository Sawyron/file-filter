package org.sawyron.config;

import org.sawyron.domain.filters.FilterApplicationProperties;
import org.sawyron.domain.statistics.CountStatisticsGatherer;
import org.sawyron.domain.statistics.StatisticsGatherer;
import org.sawyron.domain.tokens.floating.DoubleStatisticsRenderer;
import org.sawyron.domain.tokens.floating.FloatingStatisticsGatherer;
import org.sawyron.domain.tokens.integer.IntegerStatisticsGatherer;
import org.sawyron.domain.tokens.integer.IntegerStatisticsRenderer;
import org.sawyron.domain.tokens.strings.StringStatisticsGatherer;
import org.sawyron.domain.tokens.strings.StringStatisticsRenderer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigInteger;

@Configuration
public class StatisticsConfiguration {
    @Bean
    public StatisticsGatherer<BigInteger> bigIntegerStatisticsGatherer(
            FilterApplicationProperties properties
    ) {
        return properties.isFull()
                ? new IntegerStatisticsGatherer(new IntegerStatisticsRenderer())
                : new CountStatisticsGatherer<>("Integer:");
    }

    @Bean
    public StatisticsGatherer<Double> doubleStatisticsGatherer(
            FilterApplicationProperties properties
    ) {
        return properties.isFull()
                ? new FloatingStatisticsGatherer(new DoubleStatisticsRenderer())
                : new CountStatisticsGatherer<>("Float:");
    }

    @Bean
    public StatisticsGatherer<String> stringStatisticsGatherer(
            FilterApplicationProperties properties
    ) {
        return properties.isFull()
                ? new StringStatisticsGatherer(new StringStatisticsRenderer())
                : new CountStatisticsGatherer<>("Strings:");
    }
}
