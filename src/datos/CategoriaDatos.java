package datos;

import beans.CategoriaBeans;
import util.Conexion;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoriaDatos {

    // Método para obtener la lista de categorías desde la BD
    public List<CategoriaBeans> consultarCategorias() {
        List<CategoriaBeans> lista = new ArrayList<>();
        // Consulta a la tabla categoria definida en los requerimientos [cite: 32]
        String sql = "SELECT id_categoria, nombre_categoria FROM categoria";

        try (Connection con = Conexion.conectar();
             PreparedStatement ps = con.prepareStatement(sql); // Uso obligatorio de PreparedStatement [cite: 59]
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                CategoriaBeans categoria = new CategoriaBeans();
                categoria.setId_categoria(rs.getInt("id_categoria"));
                categoria.setNombre_categoria(rs.getString("nombre_categoria"));
                lista.add(categoria);
            }
        } catch (SQLException e) {
            // Importante para la nota de "Capa 8": registrar el error [cite: 64, 74]
            System.err.println("Error al consultar categorías: " + e.getMessage());
        }
        return lista;
    }

    public String obtenerNombreCategoria(int id) {
        String nombre = "";
        String sql = "SELECT nombre_categoria FROM categoria WHERE id_categoria = ?";
        try (Connection con = Conexion.conectar(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) nombre = rs.getString("nombre_categoria");
        } catch (SQLException e) { e.printStackTrace(); }
        return nombre;
    }
}