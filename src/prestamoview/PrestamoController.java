/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package prestamoview;

/**
 *
 * @author Usuario
 */

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PrestamoController {
    private Connection connection;

    public PrestamoController() throws SQLException {
        connection = ConexionSQL.getConnection();
    }

    public void registrarPrestamo(Prestamo prestamo) {
        String query = "INSERT INTO prestamos (libro_id, socio_id, fecha_prestamo, fecha_entrega) VALUES (?, ?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, prestamo.getLibroId());
            statement.setInt(2, prestamo.getSocioId());
            statement.setDate(3, new java.sql.Date(prestamo.getFechaPrestamo().getTime()));
            statement.setDate(4, new java.sql.Date(prestamo.getFechaEntrega().getTime()));

            statement.executeUpdate();
            System.out.println("Préstamo registrado exitosamente.");
        } catch (SQLException e) {
            System.out.println("Error al registrar el préstamo: " + e.getMessage());
        }
    }
}