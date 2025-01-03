package org.sawyron.domain.filters;

public record FilterOutputProperties(String integerOutputDir, String floatOutputDir, String stringOutputDir) {
    public static FilterOutputProperties createDefault() {
        return new FilterOutputProperties("integers", "floats", "strings");
    }
}
