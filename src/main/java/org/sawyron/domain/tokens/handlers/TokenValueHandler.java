package org.sawyron.domain.tokens.handlers;

import org.sawyron.domain.statistics.StatisticsGatherer;
import org.sawyron.domain.tokens.TokenValidateHandler;

public class TokenValueHandler<T> implements TokenValidateHandler {
    private final ValueParser<T> valueParser;
    private final StatisticsGatherer<T> statisticsGatherer;

    public TokenValueHandler(ValueParser<T> valueParser, StatisticsGatherer<T> statisticsGatherer) {
        this.valueParser = valueParser;
        this.statisticsGatherer = statisticsGatherer;
    }

    @Override
    public boolean canHandle(String token) {
        return valueParser.canParse(token);
    }

    @Override
    public void handle(String token) {
        T value = valueParser.parse(token);
        statisticsGatherer.accept(value);
    }
}
