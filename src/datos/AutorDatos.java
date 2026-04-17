package datos;

import beans.AutorBeans;
import util.Conexion;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AutorDatos {

    // Método para obtener la lista de autores (necesario para el ComboBox)
    public List<AutorBeans> consultarAutores() {
        List<AutorBeans> lista = new ArrayList<>();
        String sql = "SELECT id_autor, nombre, nacionalidad FROM autor";

        try (Connection con = Conexion.conectar();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                AutorBeans autor = new AutorBeans();
                autor.setId_autor(rs.getInt("id_autor"));
                autor.setNombre(rs.getString("nombre"));
                autor.setNacionalidad(rs.getString("nacionalidad"));
                lista.add(autor);
            }
        } catch (SQLException e) {
            System.err.println("Error al consultar autores: " + e.getMessage());
        }
        return lista;
    }

    public String obtenerNombreAutor(int id) {
        String nombre = "";
        String sql = "SELECT nombre FROM autor WHERE id_autor = ?";
        try (Connection con = Conexion.conectar(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) nombre = rs.getString("nombre");
        } catch (SQLException e) { e.printStackTrace(); }
        return nombre;
    }
}