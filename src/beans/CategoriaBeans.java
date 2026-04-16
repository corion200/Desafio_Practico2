package beans;

public class CategoriaBeans {
    private int id_categoria;
    private String nombre_categoria;

    public CategoriaBeans() {}

    public CategoriaBeans(int id_categoria, String nombre_categoria) {
        this.id_categoria = id_categoria;
        this.nombre_categoria = nombre_categoria;
    }

    // Getters y Setters (Obligatorios para el 10% de POO)
    public int getId_categoria() { return id_categoria; }
    public void setId_categoria(int id_categoria) { this.id_categoria = id_categoria; }

    public String getNombre_categoria() { return nombre_categoria; }
    public void setNombre_categoria(String nombre_categoria) { this.nombre_categoria = nombre_categoria; }

    // Para que el ComboBox de categorías muestre el texto correctamente
    @Override
    public String toString() {
        return nombre_categoria;
    }
}