package org.sawyron.domain.tokens.floating;

import org.sawyron.domain.statistics.Renderer;
import org.sawyron.domain.statistics.StatisticsGatherer;

public class FloatingStatisticsGatherer implements StatisticsGatherer<Double> {
    private long count = 0;
    private double min;
    private double max;
    private double sum;
    private double average;

    private final Renderer<DoubleStatistics> renderer;

    public FloatingStatisticsGatherer(Renderer<DoubleStatistics> renderer) {
        this.renderer = renderer;
    }

    @Override
    public void accept(Double value) {
        min = count <= 0 ? value : Math.min(min, value);
        max = count <= 0 ? value : Math.max(max, value);
        sum += value;
        count++;
        average = sum / count;
    }

    @Override
    public void show() {
        renderer.render(new DoubleStatistics(
                count,
                min,
                max,
                sum,
                average
        ));
    }
}
