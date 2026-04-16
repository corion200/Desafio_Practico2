package beans;

public class AutorBeans {
    // Atributos privados según requerimiento [cite: 31, 62]
    private int id_autor;
    private String nombre;
    private String nacionalidad;

    // Constructor vacío
    public AutorBeans() {
    }

    // Constructor con parámetros (muy útil para la capa de datos)
    public AutorBeans(int id_autor, String nombre, String nacionalidad) {
        this.id_autor = id_autor;
        this.nombre = nombre;
        this.nacionalidad = nacionalidad;
    }

    // Getters y Setters obligatorios
    public int getId_autor() {
        return id_autor;
    }

    public void setId_autor(int id_autor) {
        this.id_autor = id_autor;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNacionalidad() {
        return nacionalidad;
    }

    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

    // Para que el JComboBox muestre el nombre del autor [cite: 61]
    @Override
    public String toString() {
        return nombre;
    }
}