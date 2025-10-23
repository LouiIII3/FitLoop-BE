package fitloop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class FitloopApplication {

	public static void main(String[] args) {
		SpringApplication.run(FitloopApplication.class, args);
	}

}
