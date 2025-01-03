package org.sawyron.config;

import org.sawyron.domain.filters.FilterApplicationProperties;
import org.sawyron.domain.filters.FilterPropertiesParser;
import org.springframework.boot.ApplicationArguments;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterApplicationPropertiesConfiguration {
    @Bean
    public FilterApplicationProperties filterApplicationProperties(
            ApplicationArguments arguments,
            FilterPropertiesParser parser
    ) {
        return parser.parseProperties(arguments.getSourceArgs());
    }
}
