package datos;

import beans.LibroBeans;
import util.Conexion;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LibroDatos {

    // 1. Método para INSERTAR (Create)
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

    // 2. Método para CONSULTAR (Read)
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

    // 3. Método para ELIMINAR (Delete)
    public int eliminarLibro(int id) {
        int resultado = 0;
        String sql = "DELETE FROM libro WHERE id_libro = ?";

        try (Connection con = Conexion.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            resultado = ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error al eliminar: " + e.getMessage());
        }
        return resultado;
    }

    // 4. Método para ACTUALIZAR (Update)
    public int actualizarLibro(LibroBeans libro) {
        int resultado = 0;
        String sql = "UPDATE libro SET titulo=?, año_publicacion=?, id_autor=?, id_categoria=? WHERE id_libro=?";

        try (Connection con = Conexion.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, libro.getTitulo());
            ps.setInt(2, libro.getAño_publicacion());
            ps.setInt(3, libro.getId_autor());
            ps.setInt(4, libro.getId_categoria());
            ps.setInt(5, libro.getId_libro());

            resultado = ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error al actualizar: " + e.getMessage());
        }
        return resultado;
    }

    // 5. Método para FILTRAR
    public List<LibroBeans> consultarFiltrado(int idAutor, int idCat) {
        List<LibroBeans> lista = new ArrayList<>();
        // SQL dinámico: 1=1 es un truco para agregar condiciones fácilmente
        String sql = "SELECT * FROM libro WHERE 1=1";
        if (idAutor > 0) sql += " AND id_autor = " + idAutor;
        if (idCat > 0) sql += " AND id_categoria = " + idCat;

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
        } catch (SQLException e) { System.out.println(e.getMessage()); }
        return lista;
    }

    public boolean existeLibro(String titulo, int idAutor, int idExcluir) {
        boolean existe = false;
        // Si idExcluir > 0, es una edición (ignoramos el registro que estamos editando actualmente)
        String sql = "SELECT COUNT(*) FROM libro WHERE titulo = ? AND id_autor = ? AND id_libro != ?";
        try (Connection con = Conexion.conectar(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, titulo);
            ps.setInt(2, idAutor);
            ps.setInt(3, idExcluir);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) existe = rs.getInt(1) > 0;
        } catch (SQLException e) { e.printStackTrace(); }
        return existe;
    }
}