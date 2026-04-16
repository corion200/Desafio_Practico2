package datos;

import beans.LibroBeans;
import util.Conexion;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LibroDatos {

    // Método para insertar (Create)
    public int insertarLibro(LibroBeans libro) {
        int resultado = 0;
        String sql = "INSERT INTO libro (titulo, año_publicacion, id_autor, id_categoria) VALUES (?, ?, ?, ?)";

        try (Connection con = Conexion.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, libro.getTitulo());
            ps.setInt(2, libro.getAño_publicacion());
            ps.setInt(3, libro.getId_autor());
            ps.setInt(4, libro.getId_categoria());

            resultado = ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error al insertar: " + e.getMessage());
        }
        return resultado;
    }

    // Método para consultar todos (Read)
    public List<LibroBeans> consultarLibros() {
        List<LibroBeans> lista = new ArrayList<>();
        String sql = "SELECT * FROM libro";

        try (Connection con = Conexion.conectar();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                LibroBeans libro = new LibroBeans();
                libro.setId_libro(rs.getInt("id_libro"));
                libro.setTitulo(rs.getString("titulo"));
                libro.setAño_publicacion(rs.getInt("año_publicacion"));
                libro.setId_autor(rs.getInt("id_autor"));
                libro.setId_categoria(rs.getInt("id_categoria"));
                lista.add(libro);
            }
        } catch (SQLException e) {
            System.out.println("Error al consultar: " + e.getMessage());
        }
        return lista;
    }
}