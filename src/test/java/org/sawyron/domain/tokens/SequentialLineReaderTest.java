package org.sawyron.domain.tokens;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

class SequentialLineReaderTest {

    @Test
    void whenReadToken_thenReadTokensFromInnerReadersInGivenOrder() {
        var tokenReaders = List.of(
                new TestTokenReader(List.of("1", "3")),
                new TestTokenReader(List.of("2", "4"))
        );

        var tokens = new ArrayList<String>();
        SequentialTokenReader underTest = spy(new SequentialTokenReader(tokenReaders));
        try (underTest) {
            Optional<String> token;
            while ((token = underTest.readToken()).isPresent()) {
                tokens.add(token.get());
            }
        }
        assertAll(
                () -> assertIterableEquals(List.of("1", "2", "3", "4"), tokens),
                () -> assertTrue(tokenReaders.stream().allMatch(reader -> reader.closed)),
                () -> verify(underTest).close()
        );
    }

    @Test
    void whenReadersIsEmpty_thenReturnEmptyResult() {
        SequentialTokenReader underTest = spy(new SequentialTokenReader(List.of()));
        Optional<String> actualTokenOptional;
        try (underTest) {
            actualTokenOptional = underTest.readToken();
        }
        assertAll(
                () -> assertTrue(actualTokenOptional.isEmpty()),
                () -> verify(underTest).close()
        );
    }
}