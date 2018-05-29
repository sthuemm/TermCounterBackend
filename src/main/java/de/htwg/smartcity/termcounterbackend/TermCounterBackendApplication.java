package de.htwg.smartcity.termcounterbackend;

import de.htwg.smartcity.termcounterbackend.service.impl.WordServiceImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication

public class TermCounterBackendApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext applicationContext = SpringApplication.run(TermCounterBackendApplication.class, args);
		applicationContext.getBean(WordServiceImpl.class).initializeTestData();
	}

}
