package org.sawyron.domain.tokens.strings;

import org.sawyron.domain.statistics.Renderer;
import org.sawyron.domain.statistics.StatisticsGatherer;

public class StringStatisticsGatherer implements StatisticsGatherer<String> {
    private long count = 0;
    private int minLength;
    private int maxLength;

    private final Renderer<StringStatistics> renderer;

    public StringStatisticsGatherer(Renderer<StringStatistics> renderer) {
        this.renderer = renderer;
    }

    @Override
    public void accept(String value) {
        minLength = count <= 0 ? value.length() : Math.min(minLength, value.length());
        maxLength = count <= 0 ? value.length() : Math.max(maxLength, value.length());
        count++;
    }

    @Override
    public void show() {
        renderer.render(new StringStatistics(count, minLength, maxLength));
    }
}
