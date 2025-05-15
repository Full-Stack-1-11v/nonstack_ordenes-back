package com.perfulandia.cl.Configuracion;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;



// Configuracion de RestTemplate para consumir el microservicio de  productos
@Configuration
public class RestemplateConfig {

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();

}
}
