package org.sawyron.domain.statistics;

public class CountStatisticsGatherer<T> implements StatisticsGatherer<T> {
    private long count = 0;
    private final Renderer<CountStatistics> renderer;


    public CountStatisticsGatherer(String label) {
        this.renderer = new CountStatisticsRenderer(label);
    }

    @Override
    public void accept(T value) {
        count++;
    }

    @Override
    public void show() {
        renderer.render(new CountStatistics(count));
    }
}
