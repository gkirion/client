package com.george.client.util;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import com.george.client.services.ClientService;

@ComponentScan("com.george.client")
@SpringBootApplication
public class Launcher {

	@Autowired
	ClientService clientService;

	public static void main(String[] args) {
		SpringApplication.run(Launcher.class, args);
	}

	@PostConstruct
	public void fill() throws Exception {
		clientService.put("name", "george");
		clientService.put("age", "30");
		clientService.put("chmod", "tzopaty");
		clientService.put("node", "linux");
	}

}
