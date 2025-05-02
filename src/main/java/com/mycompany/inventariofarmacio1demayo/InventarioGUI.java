import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InventarioGUI extends JFrame implements ActionListener {

    private InventarioFarmacia inventario;

    private JButton agregarButton;
    private JButton consultarButton;
    private JButton mostrarButton;
    private JButton venderButton;
    private JButton actualizarButton;
    private JButton salirButton;

    private JTextArea inventarioTextArea;
    private JScrollPane scrollPane;

    public InventarioGUI() {
        inventario = new InventarioFarmacia();
        setTitle("Sistema de Inventario Farmacia");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLayout(new BorderLayout());

        // Panel para los botones
        JPanel botonesPanel = new JPanel(new GridLayout(3, 2, 10, 10)); // 3 filas, 2 columnas, espacios entre botones

        agregarButton = new JButton("Agregar Producto");
        consultarButton = new JButton("Consultar Producto");
        mostrarButton = new JButton("Mostrar Inventario");
        venderButton = new JButton("Vender Producto");
        actualizarButton = new JButton("Actualizar Precio");
        salirButton = new JButton("Salir");

        agregarButton.addActionListener(this);
        consultarButton.addActionListener(this);
        mostrarButton.addActionListener(this);
        venderButton.addActionListener(this);
        actualizarButton.addActionListener(this);
        salirButton.addActionListener(this);

        botonesPanel.add(agregarButton);
        botonesPanel.add(consultarButton);
        botonesPanel.add(mostrarButton);
        botonesPanel.add(venderButton);
        botonesPanel.add(actualizarButton);
        botonesPanel.add(salirButton);

        add(botonesPanel, BorderLayout.NORTH); // Panel de botones en la parte superior

        // Área para mostrar el inventario (inicialmente puede estar vacía o tener un texto informativo)
        inventarioTextArea = new JTextArea("Bienvenido al Sistema de Inventario. Las acciones se mostrarán aquí.");
        inventarioTextArea.setEditable(false);
        scrollPane = new JScrollPane(inventarioTextArea);
        add(scrollPane, BorderLayout.CENTER);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        if (command.equals("Agregar Producto")) {
            mostrarFormularioAgregarProducto();
        } else if (command.equals("Consultar Producto")) {
            mostrarFormularioConsultarProducto();
        } else if (command.equals("Mostrar Inventario")) {
            inventarioTextArea.setText(inventario.mostrarInventario());
        } else if (command.equals("Vender Producto")) {
            mostrarFormularioVenderProducto();
        } else if (command.equals("Actualizar Precio")) {
            mostrarFormularioActualizarPrecio();
        } else if (command.equals("Salir")) {
            System.exit(0);
        }
    }

    private void mostrarFormularioAgregarProducto() {
        JPanel panel = new JPanel(new GridLayout(5, 2, 5, 5));
        JTextField nombreField = new JTextField();
        JTextField codigoField = new JTextField();
        JTextField precioField = new JTextField();
        JTextField stockField = new JTextField();

        panel.add(new JLabel("Nombre:"));
        panel.add(nombreField);
        panel.add(new JLabel("Código de Barras:"));
        panel.add(codigoField);
        panel.add(new JLabel("Precio Unitario:"));
        panel.add(precioField);
        panel.add(new JLabel("Cantidad Inicial en Stock:"));
        panel.add(stockField);

        int result = JOptionPane.showConfirmDialog(this, panel, "Agregar Nuevo Producto", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            try {
                String nombre = nombreField.getText();
                String codigo = codigoField.getText();
                double precio = Double.parseDouble(precioField.getText());
                int stock = Integer.parseInt(stockField.getText());
                ProductoDTO nuevoProducto = new ProductoDTO(nombre, codigo, precio, stock);
                inventario.agregarProducto(nuevoProducto);
                inventarioTextArea.setText("Producto agregado. " + inventario.mostrarInventario()); // Actualizar el área de texto
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Por favor, ingrese valores numéricos válidos para precio y stock.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void mostrarFormularioConsultarProducto() {
        String codigo = JOptionPane.showInputDialog(this, "Ingrese el código de barras del producto a buscar:", "Consultar Producto", JOptionPane.QUESTION_MESSAGE);
        if (codigo != null && !codigo.isEmpty()) {
            ProductoDTO producto = inventario.buscarProductoPorCodigo(codigo);
            if (producto != null) {
                JOptionPane.showMessageDialog(this, "Producto encontrado:\n" + producto, "Resultado de Búsqueda", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "No se encontró ningún producto con el código: " + codigo, "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void mostrarFormularioVenderProducto() {
        JPanel panel = new JPanel(new GridLayout(2, 2, 5, 5));
        JTextField codigoField = new JTextField();
        JTextField cantidadField = new JTextField();

        panel.add(new JLabel("Código de Barras:"));
        panel.add(codigoField);
        panel.add(new JLabel("Cantidad a Vender:"));
        panel.add(cantidadField);

        int result = JOptionPane.showConfirmDialog(this, panel, "Vender Producto", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            try {
                String codigo = codigoField.getText();
                int cantidad = Integer.parseInt(cantidadField.getText());
                if (inventario.venderProducto(codigo, cantidad)) {
                    inventarioTextArea.setText("Venta realizada. " + inventario.mostrarInventario()); // Actualizar el área de texto
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Por favor, ingrese un valor numérico válido para la cantidad.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void mostrarFormularioActualizarPrecio() {
        JPanel panel = new JPanel(new GridLayout(2, 2, 5, 5));
        JTextField codigoField = new JTextField();
        JTextField precioField = new JTextField();

        panel.add(new JLabel("Código de Barras:"));
        panel.add(codigoField);
        panel.add(new JLabel("Nuevo Precio Unitario:"));
        panel.add(precioField);

        int result = JOptionPane.showConfirmDialog(this, panel, "Actualizar Precio", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            try {
                String codigo = codigoField.getText();
                double precio = Double.parseDouble(precioField.getText());
                if (inventario.actualizarPrecio(codigo, precio)) {
                    inventarioTextArea.setText("Precio actualizado. " + inventario.mostrarInventario()); // Actualizar el área de texto
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Por favor, ingrese un valor numérico válido para el precio.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(InventarioGUI::new);
    }
}