package ru.akalavan.springtelegrambot;

import lombok.extern.log4j.Log4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Log4j
public class SpringTelegramBotApplication {
	public static void main(String[] args) {
		SpringApplication.run(SpringTelegramBotApplication.class, args);
	}

}
