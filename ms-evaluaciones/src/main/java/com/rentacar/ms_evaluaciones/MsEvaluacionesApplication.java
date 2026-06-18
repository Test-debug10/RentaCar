package com.rentacar.ms_evaluaciones;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableFeignClients
@SpringBootApplication
public class MsEvaluacionesApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsEvaluacionesApplication.class, args);
	}

}
