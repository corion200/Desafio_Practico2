package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {
    // Configuración de la base de datos solicitada: biblioteca_db
    private static final String URL = "jdbc:mysql://localhost:3306/biblioteca_db";
    private static final String USER = "root"; // Tu usuario de MySQL
    private static final String PASSWORD = ""; // Tu contraseña (vacía si usas XAMPP por defecto)

    public static Connection conectar() {
        Connection con = null;
        try {
            // Intentar establecer la conexión
            con = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            System.err.println("Error de conexión: " + e.getMessage());
        }
        return con;
    }
}