package org.sawyron.domain.tokens;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.function.Consumer;
import java.util.stream.Stream;

public class TokenBatchingReader {
    private final int batchSize;
    private final Consumer<Stream<String>> tokenStreamConsumer;

    public TokenBatchingReader(int batchSize, Consumer<Stream<String>> tokenStreamConsumer) {
        this.batchSize = batchSize;
        this.tokenStreamConsumer = tokenStreamConsumer;
    }


    public void readTokens(InputStream in) {
        try (var reader = new BufferedReader(new InputStreamReader(in))) {
            var batch = new ArrayList<String>(batchSize);
            reader.lines().forEach(line -> {
                if (batch.size() >= batchSize) {
                    tokenStreamConsumer.accept(batch.stream());
                    batch.clear();
                    batch.ensureCapacity(batchSize);
                }
                batch.add(line);
            });
            if (!batch.isEmpty()) {
                tokenStreamConsumer.accept(batch.stream());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
