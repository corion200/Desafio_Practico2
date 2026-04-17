package vista;

import beans.*;
import datos.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.util.Calendar;

public class FrmBiblioteca extends JFrame {
    private LibroDatos libroDAO = new LibroDatos();
    private AutorDatos autorDAO = new AutorDatos();
    private CategoriaDatos categoriaDAO = new CategoriaDatos();

    private JTextField txtTitulo, txtAnio;
    private JComboBox<Object> cmbAutor, cmbCategoria;
    private JComboBox<Object> cmbFiltroAutor, cmbFiltroCat;
    private JTable tblLibros;
    private DefaultTableModel modelo;
    private JButton btnGuardar, btnEditar, btnEliminar, btnLimpiar, btnFiltrar, btnVerTodos;

    public FrmBiblioteca() {
        initComponents();
        cargarCombos();
        listarLibros();
    }

    private void initComponents() {
        setTitle("Gestión de Biblioteca Digital - Desafío #02");
        setSize(1100, 800);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout(15, 15));

        // PALETA DE COLORES
        Color fondo = new Color(245, 247, 250);
        Color azul = new Color(52, 152, 219);
        Color morado = new Color(155, 89, 182);
        Color rojo = new Color(231, 76, 60);
        Color verde = new Color(46, 204, 113);
        Color naranja = new Color(230, 126, 34);

        getContentPane().setBackground(fondo);

        // --- PANEL NORTE: FORMULARIO ---
        JPanel panelNorte = new JPanel(new GridLayout(3, 4, 15, 15));
        panelNorte.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(azul), "REGISTRO DE LIBROS"));
        panelNorte.setBackground(Color.WHITE);

        txtTitulo = new JTextField();
        txtAnio = new JTextField();
        cmbAutor = new JComboBox<>();
        cmbCategoria = new JComboBox<>();

        panelNorte.add(new JLabel("Título del Libro:", JLabel.RIGHT)); panelNorte.add(txtTitulo);
        panelNorte.add(new JLabel("Año Publicación:", JLabel.RIGHT)); panelNorte.add(txtAnio);
        panelNorte.add(new JLabel("Seleccionar Autor:", JLabel.RIGHT)); panelNorte.add(cmbAutor);
        panelNorte.add(new JLabel("Seleccionar Categoría:", JLabel.RIGHT)); panelNorte.add(cmbCategoria);

        btnGuardar = new JButton("Guardar Libro");
        btnEditar = new JButton("Actualizar");
        btnEliminar = new JButton("Eliminar");
        btnLimpiar = new JButton("Limpiar Formulario");

        // Ajuste de Botones: Fondo de color, Letra NEGRA
        JButton[] btnsCrud = {btnGuardar, btnEditar, btnEliminar, btnLimpiar};
        for (JButton b : btnsCrud) {
            b.setForeground(Color.BLACK); // Letra en negro para legibilidad
            b.setFocusPainted(false);
            b.setFont(new Font("Segoe UI", Font.BOLD, 12));
        }
        btnGuardar.setBackground(azul);
        btnEditar.setBackground(morado);
        btnEliminar.setBackground(rojo);
        btnLimpiar.setBackground(verde);

        panelNorte.add(btnGuardar); panelNorte.add(btnEditar);
        panelNorte.add(btnEliminar); panelNorte.add(btnLimpiar);
        add(panelNorte, BorderLayout.NORTH);

        // --- PANEL CENTRAL: TABLA ---
        modelo = new DefaultTableModel(new Object[]{"ID", "Título", "Año", "Autor", "Categoría"}, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        tblLibros = new JTable(modelo);

        // Ajuste Encabezados: Fondo azul, Letra NEGRA
        tblLibros.getTableHeader().setBackground(azul);
        tblLibros.getTableHeader().setForeground(Color.BLACK);
        tblLibros.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        tblLibros.setRowHeight(25);

        JScrollPane scroll = new JScrollPane(tblLibros);
        scroll.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(scroll, BorderLayout.CENTER);

        // --- PANEL SUR: FILTROS ---
        JPanel panelSur = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 15));
        panelSur.setBackground(new Color(236, 240, 241));
        panelSur.setBorder(BorderFactory.createTitledBorder("BÚSQUEDA Y FILTRADO"));

        cmbFiltroAutor = new JComboBox<>();
        cmbFiltroCat = new JComboBox<>();
        btnFiltrar = new JButton("Filtrar Registros");
        btnVerTodos = new JButton("Mostrar Todo");

        btnFiltrar.setBackground(naranja); btnFiltrar.setForeground(Color.BLACK);
        btnVerTodos.setBackground(new Color(189, 195, 199)); btnVerTodos.setForeground(Color.BLACK);

        panelSur.add(new JLabel("Autor:")); panelSur.add(cmbFiltroAutor);
        panelSur.add(new JLabel("Categoría:")); panelSur.add(cmbFiltroCat);
        panelSur.add(btnFiltrar); panelSur.add(btnVerTodos);
        add(panelSur, BorderLayout.SOUTH);

        // EVENTOS
        btnGuardar.addActionListener(e -> guardarLibro());
        btnEditar.addActionListener(e -> editarLibro());
        btnEliminar.addActionListener(e -> eliminarLibro());
        btnLimpiar.addActionListener(e -> limpiarCampos());
        btnFiltrar.addActionListener(e -> filtrarLibros());
        btnVerTodos.addActionListener(e -> listarLibros());

        tblLibros.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) { seleccionarFila(); }
        });
    }

    private void cargarCombos() {
        cmbAutor.removeAllItems(); cmbCategoria.removeAllItems();
        cmbFiltroAutor.removeAllItems(); cmbFiltroCat.removeAllItems();
        cmbFiltroAutor.addItem("--- Todos los Autores ---");
        cmbFiltroCat.addItem("--- Todas las Categorías ---");

        for (AutorBeans a : autorDAO.consultarAutores()) {
            cmbAutor.addItem(a); cmbFiltroAutor.addItem(a);
        }
        for (CategoriaBeans c : categoriaDAO.consultarCategorias()) {
            cmbCategoria.addItem(c); cmbFiltroCat.addItem(c);
        }
    }

    private void listarLibros() {
        modelo.setRowCount(0);
        actualizarTabla(libroDAO.consultarLibros());
    }

    private void filtrarLibros() {
        int idAut = 0; int idCat = 0;
        if (cmbFiltroAutor.getSelectedItem() instanceof AutorBeans)
            idAut = ((AutorBeans) cmbFiltroAutor.getSelectedItem()).getId_autor();
        if (cmbFiltroCat.getSelectedItem() instanceof CategoriaBeans)
            idCat = ((CategoriaBeans) cmbFiltroCat.getSelectedItem()).getId_categoria();

        List<LibroBeans> lista = libroDAO.consultarFiltrado(idAut, idCat);
        modelo.setRowCount(0);
        actualizarTabla(lista);
        if(lista.isEmpty()) JOptionPane.showMessageDialog(this, "No se encontraron coincidencias.");
    }

    private void actualizarTabla(List<LibroBeans> lista) {
        for (LibroBeans l : lista) {
            modelo.addRow(new Object[]{
                    l.getId_libro(), l.getTitulo(), l.getAño_publicacion(),
                    autorDAO.obtenerNombreAutor(l.getId_autor()),
                    categoriaDAO.obtenerNombreCategoria(l.getId_categoria())
            });
        }
    }

    private void guardarLibro() {
        try {
            String titulo = txtTitulo.getText().trim();
            String anioTxt = txtAnio.getText().trim();

            if (titulo.isEmpty() || anioTxt.isEmpty()) {
                JOptionPane.showMessageDialog(this, "ERROR: El título y el año son obligatorios."); return;
            }

            int anio = Integer.parseInt(anioTxt);
            int anioActual = Calendar.getInstance().get(Calendar.YEAR);
            if (anio < 0 || anio > anioActual) {
                JOptionPane.showMessageDialog(this, "ERROR: Año no válido. Máximo permitido: " + anioActual); return;
            }

            AutorBeans autor = (AutorBeans) cmbAutor.getSelectedItem();
            if (libroDAO.existeLibro(titulo, autor.getId_autor(), 0)) {
                JOptionPane.showMessageDialog(this, "ERROR: Este libro ya existe con el mismo autor."); return;
            }

            LibroBeans l = new LibroBeans();
            l.setTitulo(titulo); l.setAño_publicacion(anio);
            l.setId_autor(autor.getId_autor());
            l.setId_categoria(((CategoriaBeans) cmbCategoria.getSelectedItem()).getId_categoria());

            if (libroDAO.insertarLibro(l) > 0) {
                JOptionPane.showMessageDialog(this, "¡Éxito! Libro registrado.");
                listarLibros(); limpiarCampos();
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "ERROR: El año debe ser un número entero.");
        }
    }

    private void editarLibro() {
        int fila = tblLibros.getSelectedRow();
        if (fila == -1) { JOptionPane.showMessageDialog(this, "Seleccione un registro para actualizar."); return; }

        try {
            int idActual = (int) modelo.getValueAt(fila, 0);
            String titulo = txtTitulo.getText().trim();
            int anio = Integer.parseInt(txtAnio.getText().trim());
            int anioActual = Calendar.getInstance().get(Calendar.YEAR);

            if (anio < 0 || anio > anioActual) {
                JOptionPane.showMessageDialog(this, "ERROR: Año no válido."); return;
            }

            AutorBeans autor = (AutorBeans) cmbAutor.getSelectedItem();
            if (libroDAO.existeLibro(titulo, autor.getId_autor(), idActual)) {
                JOptionPane.showMessageDialog(this, "ERROR: Ya existe otro registro idéntico."); return;
            }

            LibroBeans l = new LibroBeans();
            l.setId_libro(idActual); l.setTitulo(titulo); l.setAño_publicacion(anio);
            l.setId_autor(autor.getId_autor());
            l.setId_categoria(((CategoriaBeans) cmbCategoria.getSelectedItem()).getId_categoria());

            if (libroDAO.actualizarLibro(l) > 0) {
                JOptionPane.showMessageDialog(this, "¡Éxito! Registro actualizado.");
                listarLibros(); limpiarCampos();
            }
        } catch (Exception e) { JOptionPane.showMessageDialog(this, "ERROR: Verifique los datos."); }
    }

    private void eliminarLibro() {
        int fila = tblLibros.getSelectedRow();
        if (fila == -1) { JOptionPane.showMessageDialog(this, "Seleccione un libro para eliminar."); return; }
        int id = (int) modelo.getValueAt(fila, 0);
        if (JOptionPane.showConfirmDialog(this, "¿Eliminar ID " + id + "?", "Confirmar", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            if (libroDAO.eliminarLibro(id) > 0) {
                JOptionPane.showMessageDialog(this, "Eliminado exitosamente.");
                listarLibros(); limpiarCampos();
            }
        }
    }

    private void seleccionarFila() {
        int fila = tblLibros.getSelectedRow();
        if (fila != -1) {
            txtTitulo.setText(modelo.getValueAt(fila, 1).toString());
            txtAnio.setText(modelo.getValueAt(fila, 2).toString());
        }
    }

    private void limpiarCampos() {
        txtTitulo.setText(""); txtAnio.setText("");
        if (cmbAutor.getItemCount() > 0) cmbAutor.setSelectedIndex(0);
        if (cmbCategoria.getItemCount() > 0) cmbCategoria.setSelectedIndex(0);
        tblLibros.clearSelection();
    }

    public static void main(String[] args) {
        try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); } catch (Exception e) {}
        SwingUtilities.invokeLater(() -> new FrmBiblioteca().setVisible(true));
    }
}