package org.sawyron.domain.filters;

import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexpStringPredicate implements Predicate<String> {
    private final Pattern pattern;

    public RegexpStringPredicate(Pattern pattern) {
        this.pattern = pattern;
    }

    @Override
    public boolean test(String s) {
        Matcher matcher = pattern.matcher(s);
        return matcher.find() && matcher.group().equals(s);
    }
}
