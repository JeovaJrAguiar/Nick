package api.nick;

import api.nick.service.NickBotService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class NickBotApiApplication {
	public static void main(String[] args) throws Exception {
		SpringApplication.run(NickBotApiApplication.class, args);
		NickBotService.training();
	}
}
