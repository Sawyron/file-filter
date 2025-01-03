package org.sawyron.domain.tokens.integer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigInteger;

import static org.junit.jupiter.api.Assertions.*;

class BigIntegerValueParserTest {
    private BigIntegerValueParser valueParser;

    @BeforeEach
    void setup() {
        valueParser = new BigIntegerValueParser();
    }

    @ParameterizedTest
    @ValueSource(strings = {"0", "-1", "1234567890123456789", "+123"})
    void whenTokensAreValid_thenCanParseReturnsTrueAndParseReturnsValidBigInteger(String input) {
        assertAll(
                () -> assertTrue(valueParser.canParse(input), "Token should be parsable: " + input),
                () -> assertEquals(new BigInteger(input), valueParser.parse(input))
        );
    }

    @ParameterizedTest
    @ValueSource(strings = {"abc", "123.456", "-123abc", "", "123 123"})
    void testCanParse_InvalidCases(String input) {
        assertFalse(valueParser.canParse(input), "Token should not be parsable: " + input);
    }

    @ParameterizedTest
    @ValueSource(strings = {"abc", "123.456", "-123abc"})
    void whenTokenIsInvalid_thenThrowNumberFormatException(String input) {
        assertThrows(NumberFormatException.class, () -> valueParser.parse(input),
                "Expected exception for token: " + input);
    }
}