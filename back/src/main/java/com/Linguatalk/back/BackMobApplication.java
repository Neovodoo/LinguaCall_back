package com.Linguatalk.back;

import com.Linguatalk.back.service.TranslationService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BackMobApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackMobApplication.class, args);
		System.out.println("Приложение работает");
	}

}
