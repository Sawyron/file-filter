package org.sawyron.domain.statistics;

public interface StatisticsGatherer<T> extends StatisticsUi {
    void accept(T value);
}
