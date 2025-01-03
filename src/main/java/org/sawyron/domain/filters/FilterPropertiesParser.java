package org.sawyron.domain.filters;

import org.apache.commons.cli.*;
import org.springframework.stereotype.Component;

import java.nio.file.Path;
import java.util.List;
import java.util.Objects;

@Component
public class FilterPropertiesParser {
    private final Options options = createOptions();

    public FilterApplicationProperties parseProperties(String[] args) {
        CommandLineParser parser = new DefaultParser();
        try {
            CommandLine line = parser.parse(options, args);
            List<Path> files = line.getArgList().stream()
                    .map(Path::of)
                    .toList();
            return new FilterApplicationProperties(
                    Objects.requireNonNullElse(line.getOptionValue("p"), ""),
                    Objects.requireNonNullElse(line.getParsedOptionValue("o"), Path.of("")),
                    line.hasOption("a"),
                    line.hasOption("f"),
                    line.hasOption("h"),
                    files,
                    FilterOutputProperties.createDefault()
            );
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public void printHelp(HelpFormatter formatter) {
        formatter.printHelp("filter", options);
    }

    private Options createOptions() {
        var options = new Options();
        Option prefix = Option.builder("p")
                .longOpt("prefix")
                .argName("prefix")
                .hasArg()
                .build();
        Option output = Option.builder("o")
                .longOpt("output")
                .argName("output")
                .hasArg()
                .type(Path.class)
                .build();
        Option append = Option.builder("a")
                .longOpt("append")
                .build();
        Option simpleStats = Option.builder("s")
                .longOpt("simple")
                .build();
        Option fullStats = Option.builder("f")
                .longOpt("full")
                .build();
        Option help = Option.builder("h")
                .longOpt("help")
                .build();
        options.addOption(prefix);
        options.addOption(output);
        options.addOption(append);
        options.addOption(simpleStats);
        options.addOption(fullStats);
        options.addOption(help);
        return options;
    }
}
