package org.sawyron.domain.filters;

import java.nio.file.Path;
import java.util.List;

public record FilterApplicationProperties(
        String prefix,
        Path outputDir,
        boolean isAppend,
        boolean isFull,
        boolean needHelp,
        List<Path> paths,
        FilterOutputProperties outputProperties
) {
}
