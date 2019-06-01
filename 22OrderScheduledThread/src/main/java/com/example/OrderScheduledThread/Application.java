package com.example.OrderScheduledThread;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;

@SpringBootApplication
@EnableAsync
@RestController
@EnableScheduling
public class Application{

	@Autowired
	private GitHubLookupService gitHubLookupService;

	public static CompletableFuture<User> page1;
	public static CompletableFuture<User> page2;
	public static CompletableFuture<User> page3;

	public static long start;

	public static void main(String[] args) throws ExecutionException, InterruptedException {
		start = System.currentTimeMillis();

		ConfigurableApplicationContext context =SpringApplication.run(Application.class, args);

		CompletableFuture.allOf(page1,page2,page3).join();
		System.out.println("Elapsed time: " + (System.currentTimeMillis() - start));
		//web can get all output on same time
		System.out.println("--> " + page1.get());
		System.out.println("--> " + page2.get());
		System.out.println("--> " + page3.get());
	}



	@Component
	@Order(1)
	class classA implements CommandLineRunner{
		@Override
		public void run(String... args) throws Exception {
			page1 = gitHubLookupService.findUser("PivotalSoftware");
		}
	}
	@Component
	@Order(2)
	class classB implements CommandLineRunner{
		@Override
		public void run(String... args) throws Exception {
			page2 = gitHubLookupService.findUser("CloudFoundry");
		}
	}
	@Component
	@Order(3)
	class classC implements ApplicationRunner {
		@Override
		public void run(ApplicationArguments args) throws Exception {
			page3 = gitHubLookupService.findUser("Spring-Projects");
		}
	}
	@Component
	@Order(4)
	class classD implements ApplicationRunner {
		@Override
		public void run(ApplicationArguments args) throws Exception {

		}
	}
	@Bean
	public Executor taskExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(2);
		executor.setMaxPoolSize(2);
		executor.setQueueCapacity(500);
		executor.setThreadNamePrefix("GithubLookup-");
		executor.initialize();
		return executor;
	}

	@Bean
	public RestTemplate restTemplate(){
		return new RestTemplate();
	}

}
