package com.perfulandia.cl.microservicio_orden;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;


@SpringBootApplication
@EnableFeignClients //habilita el uso de feign client
public class MicroservicioOrdenApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicroservicioOrdenApplication.class, args);
	}

}
