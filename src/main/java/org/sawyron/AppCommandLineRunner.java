package org.sawyron;

import org.apache.commons.cli.HelpFormatter;
import org.sawyron.domain.exeptions.ExceptionHandler;
import org.sawyron.domain.filters.FileFilter;
import org.sawyron.domain.filters.FilterApplicationProperties;
import org.sawyron.domain.filters.FilterPropertiesParser;
import org.sawyron.domain.statistics.StatisticsService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.nio.file.Files;

@Component
public class AppCommandLineRunner implements CommandLineRunner {
    private final FileFilter filter;
    private final FilterPropertiesParser parser;
    private final ExceptionHandler exceptionHandler;
    private final FilterApplicationProperties properties;
    private final StatisticsService statisticsService;

    public AppCommandLineRunner(
            FileFilter filter, FilterPropertiesParser parser, ExceptionHandler exceptionHandler,
            FilterApplicationProperties properties,
            StatisticsService statisticsService
    ) {
        this.filter = filter;
        this.parser = parser;
        this.exceptionHandler = exceptionHandler;
        this.properties = properties;
        this.statisticsService = statisticsService;
    }

    @Override
    public void run(String... args) throws Exception {
        try {
            if (properties.needHelp()) {
                parser.printHelp(new HelpFormatter());
            }
            Files.createDirectories(properties.outputDir());
            filter.processFiles(properties.paths());
            statisticsService.show();
        } catch (Exception e) {
            exceptionHandler.handle(e);
        }
    }
}
