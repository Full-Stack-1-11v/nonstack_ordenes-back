package com.perfulandia.cl.microservicio_orden.dto;

public class EnvioDTO {


    private int idEnvio;

    //Constructor vacio
    public EnvioDTO() {

    }

    public EnvioDTO(int idEnvio) {
        this.idEnvio = idEnvio;
    }

    //Getters y Setters
    public int getIdEnvio() {
        return idEnvio;
    }

    public void serIdEnvio(int idEnvio) {
        this.idEnvio = idEnvio;
    }

    @Override
    public String toString() {
        return "EnvioDTO{" +
                "idEnvio=" + idEnvio +
                '}';
    }

}
