package com.mycompany.inventariofarmacio1demayo;

public class ProductoDTO {
    private String nombre;
    private String codigoBarras;
    private double precioUnitario;
    private int cantidadStock;

    public ProductoDTO(String nombre, String codigoBarras, double precioUnitario, int cantidadStock) {
        this.nombre = nombre;
        this.codigoBarras = codigoBarras;
        this.precioUnitario = precioUnitario;
        this.cantidadStock = cantidadStock;
    }

    // Getters
    public String getNombre() {
        return nombre;
    }

    public String getCodigoBarras() {
        return codigoBarras;
    }

    public double getPrecioUnitario() {
        return precioUnitario;
    }

    public int getCantidadStock() {
        return cantidadStock;
    }

    // Setters
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setCodigoBarras(String codigoBarras) {
        this.codigoBarras = codigoBarras;
    }

    public void setPrecioUnitario(double precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public void setCantidadStock(int cantidadStock) {
        this.cantidadStock = cantidadStock;
    }

    @Override
    public String toString() {
        return "Nombre: " + nombre + ", Código: $" + codigoBarras + ", Precio: " + String.format("%.2f", precioUnitario) + ", Stock: " + cantidadStock;
    }
}