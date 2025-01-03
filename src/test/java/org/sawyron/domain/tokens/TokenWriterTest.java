package org.sawyron.domain.tokens;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TokenWriterTest {
    private static class ByteArrayTokenWriter extends TokenWriter {
        final ByteArrayOutputStream out;

        private ByteArrayTokenWriter(ByteArrayOutputStream out) {
            this.out = out;
        }

        @Override
        protected BufferedWriter createWriter() {
            return new BufferedWriter(new OutputStreamWriter(out));
        }
    }

    @Spy
    @InjectMocks
    private ByteArrayTokenWriter writer;

    @Spy
    private ByteArrayOutputStream out;


    @Test
    void whenWrite_thenCreateWriterOnce() {
        List<String> tokens = IntStream.range(0, 10)
                .mapToObj(i -> "token_" + i)
                .toList();

        tokens.forEach(writer::write);

        assertDoesNotThrow(writer::close);
        assertAll(
                () -> verify(writer, times(1)).createWriter(),
                () -> assertIterableEquals(tokens, Arrays.asList(out.toString().split(System.lineSeparator()))),
                () -> verify(out, times(1)).close()
        );
    }

    @Test
    void whenWriteIsNotCalled_thenDoNotCreateWriter() {
        assertDoesNotThrow(writer::close);
        assertAll(
                () -> verify(writer, never()).createWriter(),
                () -> assertDoesNotThrow(() -> verify(out, never()).close())
        );
    }
}