package org.sawyron.domain.tokens.integer;

import org.sawyron.domain.statistics.Renderer;

public class IntegerStatisticsRenderer implements Renderer<IntegerStatistics> {
    @Override
    public void render(IntegerStatistics subject) {
        String text = subject.count() <= 0
                ? "Not found."
                : """
                Count: %s
                Min: %s
                Max: %s
                Sum: %s
                Average: %.5f"""
                .formatted(
                        subject.count(),
                        subject.min(),
                        subject.max(),
                        subject.sum(),
                        subject.average()
                );
        System.out.println("Integer:");
        System.out.println(text);
    }
}
