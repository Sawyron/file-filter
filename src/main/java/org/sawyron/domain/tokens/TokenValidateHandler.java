package org.sawyron.domain.tokens;

public interface TokenValidateHandler extends TokenHandler {
    boolean canHandle(String token);
}
