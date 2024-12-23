package sisibibi.wanttogram;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class WantToGramApplication {

	public static void main(String[] args) {
		SpringApplication.run(WantToGramApplication.class, args);
	}

}
