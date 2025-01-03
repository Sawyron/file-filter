package org.sawyron.domain.tokens.integer;

import org.sawyron.domain.statistics.Renderer;
import org.sawyron.domain.statistics.StatisticsGatherer;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;

public class IntegerStatisticsGatherer implements StatisticsGatherer<BigInteger> {
    private long count = 0;
    private BigInteger min = BigInteger.ZERO;
    private BigInteger max = BigInteger.ZERO;
    private BigInteger sum = BigInteger.ZERO;
    private BigDecimal average = BigDecimal.ZERO;

    private final Renderer<IntegerStatistics> renderer;

    public IntegerStatisticsGatherer(Renderer<IntegerStatistics> renderer) {
        this.renderer = renderer;
    }

    public void accept(BigInteger bigInteger) {
        min = count <= 0 ? bigInteger : min.min(bigInteger);
        max = count <= 0 ? bigInteger : max.max(bigInteger);
        sum = sum.add(bigInteger);
        count++;
        average = new BigDecimal(sum)
                .setScale(5, RoundingMode.HALF_UP)
                .divide(new BigDecimal(count), RoundingMode.HALF_UP);
    }

    @Override
    public void show() {
        renderer.render(new IntegerStatistics(
                count,
                min,
                max,
                sum,
                average
        ));
    }
}
