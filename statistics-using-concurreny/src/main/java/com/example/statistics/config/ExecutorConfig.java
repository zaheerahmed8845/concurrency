package com.example.statistics.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class ExecutorConfig {

    /*private final WriterProcessorConfig writerProcessorConfig;

    private final ReaderProcessorConfig readerProcessorConfig;

    @Bean
    @Scope(BeanDefinition.SCOPE_PROTOTYPE)
    public ExecutorService writeExecutor(){
        return Executors.newFixedThreadPool(writerProcessorConfig.getNoOfThreads());
    }


    @Bean
    @Scope(BeanDefinition.SCOPE_PROTOTYPE)
    public ExecutorService readExecutor(){
        return Executors.newFixedThreadPool(readerProcessorConfig.getNoOfThreads());
    }*/
}
