package net.codecraft.jejutrip;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class JejutripApplication {

	public static void main(String[] args) {
		SpringApplication.run(JejutripApplication.class, args);
	}

}
