package org.sawyron.domain.tokens.floating;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.sawyron.domain.statistics.Renderer;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class FloatingStatisticsGathererTest {
    public static final double DELTA = 1e-10;

    private FloatingStatisticsGatherer gatherer;

    @Mock
    private Renderer<DoubleStatistics> rendererMock;

    @BeforeEach
    void setUp() {
        gatherer = new FloatingStatisticsGatherer(rendererMock);
    }

    @Test
    void whenAcceptMultipleValues_thenStatisticsAreCorrect() {
        gatherer.accept(10.0);
        gatherer.accept(20.5);
        gatherer.accept(-5.2);

        gatherer.show();

        ArgumentCaptor<DoubleStatistics> captor = ArgumentCaptor.forClass(DoubleStatistics.class);
        verify(rendererMock).render(captor.capture());

        DoubleStatistics stats = captor.getValue();
        assertAll(
                () -> assertEquals(3L, stats.count()),
                () -> assertEquals(-5.2, stats.min()),
                () -> assertEquals(20.5, stats.max()),
                () -> assertEquals(25.3, stats.sum(), DELTA),
                () -> assertEquals(25.3 / 3, stats.average(), DELTA)
        );
    }

    @Test
    void whenNoValuesAccepted_thenStatisticsAreDefault() {
        gatherer.show();

        ArgumentCaptor<DoubleStatistics> captor = ArgumentCaptor.forClass(DoubleStatistics.class);
        verify(rendererMock).render(captor.capture());

        DoubleStatistics stats = captor.getValue();
        assertAll(
                () -> assertEquals(0L, stats.count()),
                () -> assertEquals(0.0, stats.min()),
                () -> assertEquals(0.0, stats.max()),
                () -> assertEquals(0.0, stats.sum()),
                () -> assertEquals(0.0, stats.average())
        );
    }
}