package org.sawyron.domain.exeptions;

import java.io.IOException;
import java.nio.file.NoSuchFileException;

public class DefaultExceptionHandler implements ExceptionHandler {
    @Override
    public void handle(Exception exception) {
        switch (exception) {
            case NoSuchFileException noSuchFileException ->
                    System.out.printf("Can not open file: %s%n", noSuchFileException.getLocalizedMessage());
            case IOException ioException -> System.out.printf("Problems with i/o occurred: %s", ioException);
            default -> System.out.printf("An exception occurred: %s%n", exception.getLocalizedMessage());
        }
    }
}
