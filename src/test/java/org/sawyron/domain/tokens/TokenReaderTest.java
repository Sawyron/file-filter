package org.sawyron.domain.tokens;

import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.mockito.Mockito.*;

class TokenReaderTest {

    @Test
    void whenAsStreamAcceptTokenReader_thenReturnSteamOfAllTokens() {
        var tokens = IntStream.range(0, 10).mapToObj(i -> "token_" + i).toList();
        TestTokenReader tokenReader = spy(new TestTokenReader(tokens));

        var actualTokens = TokenReader.asStream(tokenReader).toList();

        assertAll(
                () -> assertIterableEquals(tokens, actualTokens),
                () -> verify(tokenReader, times(tokens.size() + 1)).readToken()
        );
    }
}