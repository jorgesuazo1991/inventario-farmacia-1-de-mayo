package com.mycompany.inventariofarmacio1demayo;

import javax.swing.JOptionPane;

public class InventarioFarmacia {

    private ProductoDTO[] productos;
    private int contadorProductos;

    public InventarioFarmacia() {
        productos = new ProductoDTO[100];
        contadorProductos = 0;
    }

    /**
     * Agrega un producto nuevo al inventario.
     * @param producto El objeto ProductoDTO a agregar.
     */
    public void agregarProducto(ProductoDTO producto) {
        if (contadorProductos < productos.length) {
            productos[contadorProductos++] = producto;
            JOptionPane.showMessageDialog(null, "Producto agregado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "El inventario está lleno.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Busca un producto por su código de barras.
     * @param codigoBarras El código de barras del producto a buscar.
     * @return El objeto ProductoDTO si se encuentra, null si no.
     */
    public ProductoDTO buscarProductoPorCodigo(String codigoBarras) {
        for (int i = 0; i < contadorProductos; i++) {
            if (productos[i].getCodigoBarras().equals(codigoBarras)) {
                return productos[i];
            }
        }
        return null;
    }

    /**
     * Muestra todos los productos registrados en el inventario.
     * @return Una cadena de texto con la información de los productos.
     */
    public String mostrarInventario() {
        StringBuilder sb = new StringBuilder("--- Inventario de la Farmacia ---\n");
        if (contadorProductos == 0) {
            sb.append("El inventario está vacío.\n");
        } else {
            for (int i = 0; i < contadorProductos; i++) {
                sb.append(productos[i].toString()).append("\n");
            }
        }
        return sb.toString();
    }

    /**
     * Resta unidades al stock de un producto si hay suficiente.
     * @param codigoBarras El código de barras del producto a vender.
     * @param cantidadVendida La cantidad de unidades a vender.
     * @return true si la venta se realizó con éxito, false en caso contrario.
     */
    public boolean venderProducto(String codigoBarras, int cantidadVendida) {
        ProductoDTO producto = buscarProductoPorCodigo(codigoBarras);
        if (producto != null) {
            if (producto.getCantidadStock() >= cantidadVendida) {
                producto.setCantidadStock(producto.getCantidadStock() - cantidadVendida);
                JOptionPane.showMessageDialog(null, "Venta realizada correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "No hay suficiente stock para el producto: " + producto.getNombre(), "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        } else {
            JOptionPane.showMessageDialog(null, "No se encontró el producto con código: " + codigoBarras, "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    /**
     * Cambia el precio de un producto.
     * @param codigoBarras El código de barras del producto cuyo precio se va a actualizar.
     * @param nuevoPrecio El nuevo precio unitario del producto.
     * @return true si el precio se actualizó con éxito, false si no se encontró el producto.
     */
    public boolean actualizarPrecio(String codigoBarras, double nuevoPrecio) {
        ProductoDTO producto = buscarProductoPorCodigo(codigoBarras);
        if (producto != null) {
            producto.setPrecioUnitario(nuevoPrecio);
            JOptionPane.showMessageDialog(null, "Precio de " + producto.getNombre() + " actualizado a $" + String.format("%.2f", nuevoPrecio), "Éxito", JOptionPane.INFORMATION_MESSAGE);
            return true;
        } else {
            JOptionPane.showMessageDialog(null, "No se encontró el producto con código: " + codigoBarras, "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
}