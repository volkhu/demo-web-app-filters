package demo.web.app.filtersbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

@SpringBootApplication
public class FiltersApplication {

	public static void main(String[] args) {
		SpringApplication.run(FiltersApplication.class, args);
	}
}
