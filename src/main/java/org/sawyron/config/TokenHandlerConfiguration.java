package org.sawyron.config;

import org.sawyron.domain.statistics.StatisticsGatherer;
import org.sawyron.domain.tokens.TokenHandler;
import org.sawyron.domain.tokens.floating.FloatingTokenValidateHandlerProvider;
import org.sawyron.domain.tokens.handlers.FirstMatchTokenHandler;
import org.sawyron.domain.tokens.handlers.TokenValueHandler;
import org.sawyron.domain.tokens.handlers.ValueParser;
import org.sawyron.domain.tokens.integer.IntegerTokenValidateHandlerProvider;
import org.sawyron.domain.tokens.strings.StringTokenValidateHandlerProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigInteger;
import java.util.List;
import java.util.function.Supplier;

@Configuration
public class TokenHandlerConfiguration {
    @Bean
    public Supplier<TokenHandler> tokenFilterSupplier(
            IntegerTokenValidateHandlerProvider integerProvider,
            FloatingTokenValidateHandlerProvider floatingProvider,
            StringTokenValidateHandlerProvider stringProvider
    ) {
        return () -> new FirstMatchTokenHandler(List.of(
                integerProvider.provide(),
                floatingProvider.provide(),
                stringProvider.provide()
        ));
    }

    @Bean
    public TokenValueHandler<BigInteger> bigIntegerTokenValueHandler(
            ValueParser<BigInteger> valueParser,
            StatisticsGatherer<BigInteger> statisticsGatherer
    ) {
        return new TokenValueHandler<>(valueParser, statisticsGatherer);
    }

    @Bean
    public TokenValueHandler<Double> doubleTokenValueHandler(
            ValueParser<Double> valueParser,
            StatisticsGatherer<Double> statisticsGatherer
    ) {
        return new TokenValueHandler<>(valueParser, statisticsGatherer);
    }

    @Bean
    public TokenValueHandler<String> stringTokenValueHandler(
            ValueParser<String> valueParser,
            StatisticsGatherer<String> statisticsGatherer
    ) {
        return new TokenValueHandler<>(valueParser, statisticsGatherer);
    }
}
