package restapi.webapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

// SpringBootApplication is a meta-annotation that
// pulls in component scanning, autoconfiguration, and property support.
@SpringBootApplication
@EnableAsync
public class InventoryApplication {

	public static void main(String... args) {
		SpringApplication.run(InventoryApplication.class, args);
	}

	@Bean
	public Executor taskExecutor(){
		ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
		taskExecutor.setCorePoolSize(3);
		taskExecutor.setMaxPoolSize(5);
		taskExecutor.setKeepAliveSeconds(1);
		taskExecutor.setQueueCapacity(100);

		taskExecutor.setThreadNamePrefix("TaskExecutor");
		taskExecutor.initialize();
		return taskExecutor;
	}

}
