package org.sawyron.domain.tokens.strings;

import org.sawyron.domain.statistics.Renderer;

public class StringStatisticsRenderer implements Renderer<StringStatistics> {
    @Override
    public void render(StringStatistics subject) {
        String text = subject.count() <= 0
                ? "Not found"
                : """
                Count: %d
                Min length: %d
                Max length: %d"""
                .formatted(
                        subject.count(),
                        subject.minLength(),
                        subject.maxLength()
                );
        System.out.println("Strings:");
        System.out.println(text);
    }
}
