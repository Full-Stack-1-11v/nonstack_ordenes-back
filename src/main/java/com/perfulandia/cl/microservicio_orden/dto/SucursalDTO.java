package com.perfulandia.cl.microservicio_orden.dto;

public class SucursalDTO {



    private int idSucursal;

    private String nombreSucursal;

    

    //constructor vacios
     public SucursalDTO(){

    }
    //constructor con parametros
    public SucursalDTO(int idSucursal, String nombreSucursal, int idCiudad){
        this.idSucursal = idSucursal;
        this.nombreSucursal = nombreSucursal;
       
    }
    
    //getter and setter

    public int getIdSucursal(){
        return idSucursal;
    }

    public void setIdSucursal(int idSucursal){
        this.idSucursal = idSucursal;
    }

    public String getNombreSucursal(){
        return nombreSucursal;
    }

    public void setNombreSucursal(String nombreSucursal){
        this.nombreSucursal = nombreSucursal;
    }




}
