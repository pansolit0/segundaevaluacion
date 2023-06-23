/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package prestamoview;

/**
 *
 * @author Usuario
 */
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class PrestamoView extends JFrame {
    private JTextField libroIdTextField;
    private JTextField socioIdTextField;
    private JTextField fechaPrestamoTextField;
    private JTextField fechaEntregaTextField;
    private JTable prestamosTable;
    private DefaultTableModel tableModel;

    private PrestamoController prestamoController;

    public PrestamoView() throws SQLException {
        prestamoController = new PrestamoController();

        setTitle("Registro de Préstamo de Libros");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Panel izquierdo con el formulario de registro
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.insets = new Insets(5, 5, 5, 5);

        JLabel libroIdLabel = new JLabel("Código del libro:");
        libroIdTextField = new JTextField(15);
        JLabel socioIdLabel = new JLabel("Número de socio:");
        socioIdTextField = new JTextField(15);
        JLabel fechaPrestamoLabel = new JLabel("Fecha del préstamo (yyyy-MM-dd):");
        fechaPrestamoTextField = new JTextField(15);
        JLabel fechaEntregaLabel = new JLabel("Fecha de entrega (yyyy-MM-dd):");
        fechaEntregaTextField = new JTextField(15);
        JButton registrarButton = new JButton("Registrar Préstamo");

        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(libroIdLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        formPanel.add(libroIdTextField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(socioIdLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        formPanel.add(socioIdTextField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(fechaPrestamoLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        formPanel.add(fechaPrestamoTextField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        formPanel.add(fechaEntregaLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        formPanel.add(fechaEntregaTextField, gbc);

        gbc.gridx = 1;
        gbc.gridy = 4;
        formPanel.add(registrarButton, gbc);

        // Panel derecho con la tabla de préstamos
        JPanel tablePanel = new JPanel(new BorderLayout());
        tableModel = new DefaultTableModel();
        prestamosTable = new JTable(tableModel);
        tableModel.addColumn("Libro ID");
        tableModel.addColumn("Socio ID");
        tableModel.addColumn("Fecha de Préstamo");
        tableModel.addColumn("Fecha de Entrega");
        JScrollPane tableScrollPane = new JScrollPane(prestamosTable);
        tablePanel.add(tableScrollPane, BorderLayout.CENTER);

        // Panel principal que contiene los paneles izquierdo y derecho
        JPanel mainPanel = new JPanel(new GridLayout(1, 2));
        mainPanel.add(formPanel);
        mainPanel.add(tablePanel);

        add(mainPanel, BorderLayout.CENTER);

        registrarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int libroId = Integer.parseInt(libroIdTextField.getText());
                int socioId = Integer.parseInt(socioIdTextField.getText());
                Date fechaPrestamo = parseDate(fechaPrestamoTextField.getText());
                Date fechaEntrega = parseDate(fechaEntregaTextField.getText());

                Prestamo prestamo = new Prestamo(libroId, socioId, fechaPrestamo, fechaEntrega);
                prestamoController.registrarPrestamo(prestamo);

                actualizarTablaPrestamos();

                libroIdTextField.setText("");
                socioIdTextField.setText("");
                fechaPrestamoTextField.setText("");
                fechaEntregaTextField.setText("");
            }
        });

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private Date parseDate(String dateStr) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return format.parse(dateStr);
        } catch (ParseException e) {
            System.out.println("Error al analizar la fecha: " + e.getMessage());
            return null;
        }
    }

    private void actualizarTablaPrestamos() {
        tableModel.setRowCount(0);
        ArrayList<Prestamo> prestamos = prestamoController.obtenerPrestamos();

        for (Prestamo prestamo : prestamos) {
            Object[] rowData = { prestamo.getLibroId(), prestamo.getSocioId(), prestamo.getFechaPrestamo(), prestamo.getFechaEntrega() };
            tableModel.addRow(rowData);
        }
    }

    public static void main(String[] args) throws SQLException {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
                    new PrestamoView();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
