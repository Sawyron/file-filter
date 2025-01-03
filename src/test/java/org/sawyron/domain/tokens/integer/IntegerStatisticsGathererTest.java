package org.sawyron.domain.tokens.integer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.sawyron.domain.statistics.Renderer;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class IntegerStatisticsGathererTest {
    private IntegerStatisticsGatherer gatherer;

    @Mock
    Renderer<IntegerStatistics> rendererMock;

    @BeforeEach
    void setup() {
        gatherer = new IntegerStatisticsGatherer(rendererMock);
    }

    @Test
    void whenAcceptMultipleValues_thenStatisticsAreCorrect() {
        gatherer.accept(BigInteger.valueOf(100));
        gatherer.accept(BigInteger.valueOf(200));
        gatherer.accept(BigInteger.valueOf(-50));

        gatherer.show();

        ArgumentCaptor<IntegerStatistics> captor = ArgumentCaptor.forClass(IntegerStatistics.class);
        verify(rendererMock).render(captor.capture());

        IntegerStatistics stats = captor.getValue();
        assertAll(
                () -> assertEquals(3L, stats.count()),
                () -> assertEquals(BigInteger.valueOf(-50), stats.min()),
                () -> assertEquals(BigInteger.valueOf(200), stats.max()),
                () -> assertEquals(BigInteger.valueOf(250), stats.sum()),
                () -> assertEquals(
                        new BigDecimal("83.33").setScale(2, RoundingMode.HALF_UP),
                        stats.average().setScale(2, RoundingMode.HALF_UP)
                )
        );
    }

    @Test
    void whenNoValuesAccepted_thenStatisticsAreZero() {
        gatherer.show();

        ArgumentCaptor<IntegerStatistics> captor = ArgumentCaptor.forClass(IntegerStatistics.class);
        verify(rendererMock).render(captor.capture());

        IntegerStatistics stats = captor.getValue();
        assertAll(
                () -> assertEquals(0L, stats.count()),
                () -> assertEquals(BigInteger.ZERO, stats.min()),
                () -> assertEquals(BigInteger.ZERO, stats.max()),
                () -> assertEquals(BigInteger.ZERO, stats.sum()),
                () -> assertEquals(BigDecimal.ZERO, stats.average())
        );
    }
}