package view;

import dao.UsuarioDAO;
import model.Usuario;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class UsuarioListView extends JFrame {

    private JTable tabla;
    private DefaultTableModel modelo;
    private int id;
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public UsuarioListView() {
        setTitle("Gestión de Usuarios");
        setSize(800, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        modelo = new DefaultTableModel(
                new String[]{"ID", "Tipo", "Documento", "Nombres", "Apellidos", "Email", "Foto"}, 0
        ) {
            @Override
            public Class<?> getColumnClass(int column) {
                if (column == 6) return ImageIcon.class;
                return String.class;
            }
        };


        tabla = new JTable(modelo);
        JScrollPane scroll = new JScrollPane(tabla);

        JButton btnAgregar = new JButton("Agregar Usuario");
        JButton btnEditar = new JButton("Editar Usuario");
        JButton btnEliminar = new JButton("Eliminar Usuario");

        JPanel panelBotones = new JPanel();
        panelBotones.add(btnAgregar);
        panelBotones.add(btnEditar);
        panelBotones.add(btnEliminar);

        add(scroll, BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);

        btnAgregar.addActionListener(e -> abrirFormulario(null));
        btnEditar.addActionListener(e -> editarUsuario());
        btnEliminar.addActionListener(e -> eliminarUsuario());

        listarUsuarios();
        setVisible(true);
    }

    private void listarUsuarios() {
        modelo.setRowCount(0);
        try {
            List<Usuario> lista = new UsuarioDAO().listar();

            for (Usuario u : lista) {

                ImageIcon icon = null;

                if (u.getFoto() != null) {
                    Image img = new ImageIcon(u.getFoto()).getImage()
                            .getScaledInstance(60, 60, Image.SCALE_SMOOTH);
                    icon = new ImageIcon(img);
                }

                modelo.addRow(new Object[]{
                        u.getId(),
                        u.getTipoDocumento(),
                        u.getDocumento(),
                        u.getNombres(),
                        u.getApellidos(),
                        u.getEmail(),
                        icon
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al listar usuarios");
        }
    }

    private void abrirFormulario(Usuario usuario) {
        new UsuarioForm(this, usuario);
        listarUsuarios();
    }

    private void editarUsuario() {
        int fila = tabla.getSelectedRow();
        if (fila >= 0) {
            int id = (int) modelo.getValueAt(fila, 0);
            Usuario usuario = new UsuarioDAO().buscarPorId(id);
            abrirFormulario(usuario);
        } else {
            JOptionPane.showMessageDialog(this, "Seleccione un usuario");
        }
    }

    private void eliminarUsuario() {
        int fila = tabla.getSelectedRow();

        if (fila >= 0) {
            int id = (int) modelo.getValueAt(fila, 0);

            try {
                new UsuarioDAO().eliminar(id);
                listarUsuarios();
                JOptionPane.showMessageDialog(this, "Usuario eliminado correctamente");
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error al eliminar usuario");
            }

        } else {
            JOptionPane.showMessageDialog(this, "Seleccione un usuario");
        }
    }

}