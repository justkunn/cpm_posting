package CPM.cpm_posting;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "CPM.cpm_posting.repository")
public class CpmPostingApplication {

	public static void main(String[] args) {
		SpringApplication.run(CpmPostingApplication.class, args);
	}

}
