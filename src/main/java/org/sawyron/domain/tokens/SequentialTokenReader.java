package org.sawyron.domain.tokens;

import java.util.*;

public class SequentialTokenReader implements TokenReader {
    private final List<TokenReader> readers;
    private int currentIndex = 0;

    public SequentialTokenReader(Collection<? extends TokenReader> readers) {
        this.readers = new ArrayList<>(readers);
    }

    @Override
    public Optional<String> readToken() {
        Optional<String> line;
        var seen = new HashSet<Integer>(readers.size());
        while ((line = readers.get(currentIndex).readToken()).isEmpty() && seen.size() < readers.size()) {
            currentIndex++;
            seen.add(currentIndex);
            if (currentIndex >= readers.size()) {
                currentIndex = 0;
            }
        }
        currentIndex++;
        if (currentIndex >= readers.size()) {
            currentIndex = 0;
        }
        return line;
    }

    @Override
    public void close() {
        var exceptions = new LinkedList<Exception>();
        for (TokenReader reader : readers) {
            try {
                reader.close();
            } catch (Exception e) {
                exceptions.add(e);
            }
        }
        if (!exceptions.isEmpty()) {
            throw new RuntimeException(exceptions.getLast());
        }
    }
}
