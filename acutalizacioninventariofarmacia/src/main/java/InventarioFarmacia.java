package sistemasinventariofarmacia;

import java.io.*;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class InventarioFarmacia {
    private ArrayList<ProductoDTO> productos;
    private final String NOMBRE_ARCHIVO = "inventario.txt";

    public InventarioFarmacia() {
        this.productos = new ArrayList<>();
        cargarInventarioDesdeArchivo();
    }

    public void agregarProducto(ProductoDTO producto) {
        if (productos.size() < 100) {
            productos.add(producto);
            JOptionPane.showMessageDialog(null, "Producto agregado correctamente.");
        } else {
            JOptionPane.showMessageDialog(null, "El inventario está lleno.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public ProductoDTO buscarProductoPorCodigo(String codigoBarras) {
        for (ProductoDTO producto : productos) {
            if (producto.getCodigoBarras().equals(codigoBarras)) {
                return producto;
            }
        }
        return null;
    }

    public String mostrarInventario() {
        StringBuilder sb = new StringBuilder("--- Inventario ---\n");
        if (productos.isEmpty()) {
            sb.append("El inventario está vacío.\n");
        } else {
            for (ProductoDTO producto : productos) {
                sb.append(producto.toString()).append("\n");
            }
        }
        return sb.toString();
    }

    public boolean venderProducto(String codigoBarras, int cantidadVendida) {
        ProductoDTO producto = buscarProductoPorCodigo(codigoBarras);
        if (producto != null) {
            if (producto.getCantidadStock() >= cantidadVendida) {
                producto.setCantidadStock(producto.getCantidadStock() - cantidadVendida);
                JOptionPane.showMessageDialog(null, "Venta realizada. Nuevo stock de " + producto.getNombre() + ": " + producto.getCantidadStock());
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "No hay suficiente stock de " + producto.getNombre() + ".", "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        } else {
            JOptionPane.showMessageDialog(null, "No se encontró el producto con código: " + codigoBarras + ".", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    public boolean actualizarPrecio(String codigoBarras, double nuevoPrecio) {
        ProductoDTO producto = buscarProductoPorCodigo(codigoBarras);
        if (producto != null) {
            producto.setPrecioUnitario(nuevoPrecio);
            JOptionPane.showMessageDialog(null, "Precio de " + producto.getNombre() + " actualizado a: $" + nuevoPrecio);
            return true;
        } else {
            JOptionPane.showMessageDialog(null, "No se encontró el producto con código: " + codigoBarras + ".", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    public void guardarInventarioEnArchivo() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(NOMBRE_ARCHIVO))) {
            for (ProductoDTO producto : productos) {
                writer.println(producto.getNombre() + "," + producto.getCodigoBarras() + "," + producto.getPrecioUnitario() + "," + producto.getCantidadStock());
            }
            JOptionPane.showMessageDialog(null, "Inventario guardado en " + NOMBRE_ARCHIVO);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error al guardar el inventario: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cargarInventarioDesdeArchivo() {
        try (BufferedReader reader = new BufferedReader(new FileReader(NOMBRE_ARCHIVO))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] partes = line.split(",");
                if (partes.length == 4) {
                    String nombre = partes[0];
                    String codigoBarras = partes[1];
                    double precioUnitario = Double.parseDouble(partes[2]);
                    int cantidadStock = Integer.parseInt(partes[3]);
                    productos.add(new ProductoDTO(nombre, codigoBarras, precioUnitario, cantidadStock));
                }
            }
            JOptionPane.showMessageDialog(null, "Inventario cargado desde " + NOMBRE_ARCHIVO);
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Archivo de inventario no encontrado. Se creará uno nuevo al guardar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
        } catch (IOException | NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Error al cargar el inventario: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            productos.clear(); // Asegurarse de que la lista esté vacía en caso de error
        }
    }
}