package org.sawyron.domain.statistics;

public class CountStatisticsRenderer implements Renderer<CountStatistics> {
    private final String label;

    public CountStatisticsRenderer(String label) {
        this.label = label;
    }

    @Override
    public void render(CountStatistics subject) {
        String text = subject.count() <= 0
                ? "Not found."
                : """
                Count: %d
                """
                .formatted(subject.count());
        System.out.println(label);
        System.out.println(text);
    }
}
