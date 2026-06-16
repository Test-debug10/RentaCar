package com.rentacar.ms_seguros;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableFeignClients
@SpringBootApplication
public class MsSegurosApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsSegurosApplication.class, args);
	}

}
