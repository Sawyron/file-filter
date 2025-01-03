package org.sawyron.domain.tokens;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BatchingTokenReaderHandlerTest {
    private static class TestTokenSteamConsumer implements Consumer<Stream<String>> {
        final List<List<String>> bathes = new ArrayList<>();

        @Override
        public void accept(Stream<String> stream) {
            bathes.add(stream.toList());
        }

        List<String> getAllTokens() {
            return bathes.stream()
                    .flatMap(Collection::stream)
                    .toList();
        }
    }

    @Spy
    private TestTokenSteamConsumer testTokenSteamConsumer;


    @Test
    void whenTokenCountModBatchSizeIsZero_thenConsumeNTimes() {
        int batchSize = 10;
        int n = 2;
        var reader = new BatchingTokenReaderHandler(batchSize, testTokenSteamConsumer);
        List<String> tokens = IntStream.range(0, batchSize * n)
                .mapToObj(i -> "token_" + i)
                .toList();
        TestTokenReader tokenReaderSpy = spy(new TestTokenReader(tokens));

        reader.handleTokenReader(tokenReaderSpy);

        List<String> actualTokens = testTokenSteamConsumer.getAllTokens();
        assertAll(
                () -> verify(testTokenSteamConsumer, times(n)).accept(any()),
                () -> verify(tokenReaderSpy, times(tokens.size() + 1)).readToken(),
                () -> assertEquals(n, testTokenSteamConsumer.bathes.size()),
                () -> testTokenSteamConsumer.bathes.forEach(batch -> assertEquals(batchSize, batch.size())),
                () -> assertIterableEquals(tokens, actualTokens)
        );
    }

    @Test
    void whenTokenCountModBatchSizeIsNotZero_thenConsumeWithRemainder() {
        int batchSize = 10;
        int n = 2;
        int remainder = 5;
        var reader = new BatchingTokenReaderHandler(batchSize, testTokenSteamConsumer);
        List<String> tokens = IntStream.range(0, batchSize * n + remainder % batchSize)
                .mapToObj(i -> "token_" + i)
                .toList();
        TestTokenReader tokenReaderSpy = spy(new TestTokenReader(tokens));

        reader.handleTokenReader(tokenReaderSpy);

        List<String> actualTokens = testTokenSteamConsumer.getAllTokens();
        assertAll(
                () -> verify(testTokenSteamConsumer, times(n + 1)).accept(any()),
                () -> verify(tokenReaderSpy, times(tokens.size() + 1)).readToken(),
                () -> assertEquals(n + 1, testTokenSteamConsumer.bathes.size()),
                () -> testTokenSteamConsumer.bathes.stream()
                        .limit(n).forEach(batch -> assertEquals(batchSize, batch.size())),
                () -> assertEquals(remainder, testTokenSteamConsumer.bathes.getLast().size()),
                () -> assertIterableEquals(tokens, actualTokens)
        );
    }

    @Test
    void whenNoTokens_thenDoNotInvokeConsumer() {
        var reader = new BatchingTokenReaderHandler(10, testTokenSteamConsumer);
        TestTokenReader tokenReaderSpy = spy(new TestTokenReader(List.of()));

        reader.handleTokenReader(tokenReaderSpy);

        assertAll(
                () -> verify(testTokenSteamConsumer, never()).accept(any()),
                () -> verify(tokenReaderSpy).readToken(),
                () -> assertTrue(testTokenSteamConsumer.bathes.isEmpty())
        );
    }
}