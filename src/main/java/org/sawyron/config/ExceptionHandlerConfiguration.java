package org.sawyron.config;

import org.sawyron.domain.exeptions.DefaultExceptionHandler;
import org.sawyron.domain.exeptions.ExceptionHandler;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ExceptionHandlerConfiguration {
    @Bean
    @ConditionalOnMissingBean(ExceptionHandler.class)
    public ExceptionHandler defaultExceptionHandler() {
        return new DefaultExceptionHandler();
    }
}
