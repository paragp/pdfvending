package com.pdfvending;

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

	public static void main(String[] args) {
		SpringApplication.run(PdfvendingApplication.class, args);
	}

	@Bean
	public TaskExecutor taskExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setTaskDecorator(new MdcTaskDecorator());
		executor.setCorePoolSize(5);
		executor.setMaxPoolSize(10);
		executor.setQueueCapacity(500);
		executor.setThreadNamePrefix("PdfStorage-");
		executor.initialize();
		return executor;
	}

}
