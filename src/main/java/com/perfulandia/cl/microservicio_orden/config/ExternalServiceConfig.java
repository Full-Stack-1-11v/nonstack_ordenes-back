package com.perfulandia.cl.microservicio_orden.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import lombok.Data;

@Data
@Configuration
@ConfigurationProperties(prefix = "external")
public class ExternalServiceConfig {
    private Servicio servicioProducto;
    private Servicio servicioCliente;
    private Servicio servicioSucursal;

    @Data
    public static class Servicio {
        private Api api;
    }

    @Data
    public static class Api {
        private String baseUrl;
    }
} 