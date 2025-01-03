package org.sawyron.domain.tokens.strings;

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
class StringStatisticsGathererTest {
    private StringStatisticsGatherer gatherer;

    @Mock
    private Renderer<StringStatistics> rendererMock;

    @BeforeEach
    void setup() {
        gatherer = new StringStatisticsGatherer(rendererMock);
    }

    @Test
    void whenMultipleValuesAreAccepted_thenStatisticsAreCorrect() {
        gatherer.accept("String");
        gatherer.accept("Long string");
        gatherer.accept(".");
        gatherer.accept("...");

        gatherer.show();

        ArgumentCaptor<StringStatistics> captor = ArgumentCaptor.forClass(StringStatistics.class);
        verify(rendererMock).render(captor.capture());
        StringStatistics stats = captor.getValue();
        assertAll(
                () -> assertEquals(4, stats.count()),
                () -> assertEquals(11, stats.maxLength()),
                () -> assertEquals(1, stats.minLength())
        );
    }

    @Test
    void whenNoValuesAccepted_thenStatisticsAreZero() {
        gatherer.show();

        ArgumentCaptor<StringStatistics> captor = ArgumentCaptor.forClass(StringStatistics.class);
        verify(rendererMock).render(captor.capture());
        StringStatistics stats = captor.getValue();
        assertAll(
                () -> assertEquals(0, stats.count()),
                () -> assertEquals(0, stats.minLength()),
                () -> assertEquals(0, stats.maxLength())
        );
    }
}