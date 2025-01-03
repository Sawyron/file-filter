package org.sawyron.domain.tokens.floating;

import org.sawyron.domain.statistics.Renderer;

public class DoubleStatisticsRenderer implements Renderer<DoubleStatistics> {
    @Override
    public void render(DoubleStatistics subject) {
        String text = subject.count() <= 0
                ? "Not found"
                : """
                Count: %s
                Min: %.5f
                Max: %.5f
                Sum: %.5f
                Average: %.5f""".formatted(
                subject.count(),
                subject.min(),
                subject.max(),
                subject.sum(),
                subject.average());
        System.out.println("Float:");
        System.out.println(text);
    }
}
