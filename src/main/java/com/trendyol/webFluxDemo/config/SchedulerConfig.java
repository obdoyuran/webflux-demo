package com.trendyol.webFluxDemo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

import java.util.concurrent.Executors;

@Configuration
public class SchedulerConfig {

    private final Integer slowIOOpsThreadPoolSize;

    public SchedulerConfig(@Value("${slowIOOpsThreadPoolSize}") Integer slowIOOpsThreadPoolSize) {
        this.slowIOOpsThreadPoolSize = slowIOOpsThreadPoolSize;
    }

    @Bean
    public Scheduler slowIOScheduler() {
        return Schedulers.fromExecutor(Executors.newFixedThreadPool(slowIOOpsThreadPoolSize));
    }
}
