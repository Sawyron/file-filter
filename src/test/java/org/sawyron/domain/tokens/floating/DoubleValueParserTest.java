package org.sawyron.domain.tokens.floating;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DoubleValueParserTest {
    private DoubleValueParser valueParser;

    @BeforeEach
    void setup() {
        valueParser = new DoubleValueParser();
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "abc",
            "123abc",
            "",
            ".",
            "-123.45.67",
            "1e",
            "1e-abc",
            "1.2 2.3"
    })
    void whenTokenInvalid_thenCanParseReturnsFalse(String token) {
        assertFalse(valueParser.canParse(token), "Token should not be parsable: " + token);
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "0",
            "-1.23",
            "3.14159",
            "+2.71",
            "1E10",
            "-1.23e+3",
            "NaN",
            "Infinity",
            "-Infinity",
            "0x1.0p0",
            "0X1.0P-2"
    })
    void whenTokenValid_thenCanParseReturnsTrueAndParseReturnsValidDouble(String token) {
        double expectedValue = Double.parseDouble(token);
        assertAll(
                () -> assertTrue(valueParser.canParse(token)),
                () -> assertEquals(expectedValue, valueParser.parse(token), 1e-10)
        );
    }

    @ParameterizedTest
    @ValueSource(strings = {"abc", "123abc", "-123.45.67", "1e", "1e-abc"})
    void whenTokenInvalid_thenParseThrowsNumberFormatException(String token) {
        assertThrows(NumberFormatException.class, () -> valueParser.parse(token),
                "Expected exception for token: " + token);
    }

    @Test
    void whenTokenIsEdgeValue_thenParseIt() {
        List<String> edgeCases = List.of(
                String.valueOf(Double.MAX_VALUE),
                String.valueOf(Double.MIN_VALUE),
                String.valueOf(Double.POSITIVE_INFINITY),
                String.valueOf(Double.NEGATIVE_INFINITY),
                String.valueOf(Double.NaN)
        );
        edgeCases.forEach(
                token -> assertTrue(valueParser.canParse(token),
                        "Edge case should be parsable: " + token)
        );
    }
}