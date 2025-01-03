package org.sawyron.domain.tokens;

import java.util.ArrayList;
import java.util.function.Consumer;
import java.util.stream.Stream;

public class BatchingTokenReaderHandler {
    private final int batchSize;
    private final Consumer<Stream<String>> tokenStreamConsumer;

    public BatchingTokenReaderHandler(int batchSize, Consumer<Stream<String>> tokenStreamConsumer) {
        this.batchSize = batchSize;
        this.tokenStreamConsumer = tokenStreamConsumer;
    }

    public void handleTokenReader(TokenReader tokenReader) {
        var batch = new ArrayList<String>(batchSize);
        TokenReader.asStream(tokenReader).forEach(token -> {
            if (batch.size() >= batchSize) {
                tokenStreamConsumer.accept(batch.stream());
                batch.clear();
            }
            batch.add(token);
        });
        if (!batch.isEmpty()) {
            tokenStreamConsumer.accept(batch.stream());
        }
    }
}
