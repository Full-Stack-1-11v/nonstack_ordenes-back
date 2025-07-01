package com.perfulandia.cl.microservicio_orden.dto;
import lombok.Data;

@Data
public class ProductoDTO {
    private Integer idProducto;
    private String nombreProducto;
    private String descripcionProducto;
    private Double precioProducto;
    private Integer idCategoriaProducto;
    private Integer stockProducto;

    public Integer getIdProducto() { return idProducto; }
    public void setIdProducto(Integer idProducto) { this.idProducto = idProducto; }

    public String getNombreProducto() { return nombreProducto; }
    public void setNombreProducto(String nombreProducto) { this.nombreProducto = nombreProducto; }

    public Double getPrecioProducto() { return precioProducto; }
    public void setPrecioProducto(Double precioProducto) { this.precioProducto = precioProducto; }

    public Integer getIdCategoriaProducto() { return idCategoriaProducto; }
    public void setIdCategoriaProducto(Integer idCategoriaProducto) { this.idCategoriaProducto = idCategoriaProducto; }

    public Integer getStockProducto() { return stockProducto; }
    public void setStockProducto(Integer stockProducto) { this.stockProducto = stockProducto; }

    public String getDescripcionProducto() { return descripcionProducto; }
    public void setDescripcionProducto(String descripcionProducto) { this.descripcionProducto = descripcionProducto; }

    
}
