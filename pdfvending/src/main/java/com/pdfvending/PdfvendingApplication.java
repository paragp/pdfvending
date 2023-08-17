package com.pdfvending;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.pdfvending.config.MdcTaskDecorator;

@EnableAsync
@SpringBootApplication
public class PdfvendingApplication {

	@Value("${threadpool.corePoolSize}")
	private int corePoolSize;

	@Value("${threadpool.maxPoolSize}")
	private int maxPoolSize;

	@Value("${threadpool.queueCapacity}")
	private int queueCapacity;

	@Value("${threadpool.threadNamePrefix}")
	private String threadNamePrefix;

	public static void main(String[] args) {
		SpringApplication.run(PdfvendingApplication.class, args);
	}

	/**
	 * Defines the TaskExecutor bean for executing asynchronous tasks.
	 * Configuration values are externalized to properties or YAML file.
	 * 
	 * @return Configured ThreadPoolTaskExecutor
	 */
	@Bean
	public TaskExecutor taskExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setTaskDecorator(new MdcTaskDecorator());
		executor.setCorePoolSize(corePoolSize);
		executor.setMaxPoolSize(maxPoolSize);
		executor.setQueueCapacity(queueCapacity);
		executor.setThreadNamePrefix(threadNamePrefix);
		executor.initialize();
		return executor;
	}

}
