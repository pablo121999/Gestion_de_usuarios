package view;

import dao.UsuarioDAO;
import model.Usuario;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.nio.file.Files;

public class UsuarioForm extends JDialog {

    private JTextField txtTipo, txtDocumento, txtNombres, txtApellidos, txtEmail;
    private JLabel lblFoto;
    private byte[] fotoBytes;
    private Usuario usuario;

    public UsuarioForm(JFrame parent, Usuario usuario) {
        super(parent, true);
        this.usuario = usuario;

        setTitle(usuario == null ? "Nuevo Usuario" : "Editar Usuario");
        setSize(500, 450);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout(10, 10));

        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // ===== PANEL FORMULARIO =====
        JPanel panelForm = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        txtTipo = new JTextField(15);
        txtDocumento = new JTextField(15);
        txtNombres = new JTextField(15);
        txtApellidos = new JTextField(15);
        txtEmail = new JTextField(15);

        int y = 0;

        agregarCampo(panelForm, gbc, y++, "Tipo Documento:", txtTipo);
        agregarCampo(panelForm, gbc, y++, "Documento:", txtDocumento);
        agregarCampo(panelForm, gbc, y++, "Nombres:", txtNombres);
        agregarCampo(panelForm, gbc, y++, "Apellidos:", txtApellidos);
        agregarCampo(panelForm, gbc, y++, "Email:", txtEmail);

        panelPrincipal.add(panelForm, BorderLayout.CENTER);

        // ===== PANEL FOTO =====
        JPanel panelFoto = new JPanel(new BorderLayout(5, 5));
        panelFoto.setBorder(BorderFactory.createTitledBorder("Foto"));

        lblFoto = new JLabel();
        lblFoto.setHorizontalAlignment(SwingConstants.CENTER);
        lblFoto.setPreferredSize(new Dimension(120, 120));
        lblFoto.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));

        JButton btnFoto = new JButton("Seleccionar Foto");
        btnFoto.setFocusPainted(false);

        btnFoto.addActionListener(e -> seleccionarFoto());

        panelFoto.add(lblFoto, BorderLayout.CENTER);
        panelFoto.add(btnFoto, BorderLayout.SOUTH);

        panelPrincipal.add(panelFoto, BorderLayout.EAST);

        // ===== PANEL BOTÓN =====
        JPanel panelBoton = new JPanel();
        JButton btnGuardar = new JButton("Guardar");
        btnGuardar.setPreferredSize(new Dimension(120, 35));
        btnGuardar.setBackground(new Color(33, 150, 243));
        btnGuardar.setForeground(Color.WHITE);
        btnGuardar.setFocusPainted(false);

        btnGuardar.addActionListener(e -> guardar());

        panelBoton.add(btnGuardar);

        add(panelPrincipal, BorderLayout.CENTER);
        add(panelBoton, BorderLayout.SOUTH);

        if (usuario != null) {
            cargarDatos();
        }

        setVisible(true);
    }

    private void cargarDatos() {
        txtTipo.setText(usuario.getTipoDocumento());
        txtDocumento.setText(usuario.getDocumento());
        txtNombres.setText(usuario.getNombres());
        txtApellidos.setText(usuario.getApellidos());
        txtEmail.setText(usuario.getEmail());

        fotoBytes = usuario.getFoto();

        if (fotoBytes != null) {
            Image img = new ImageIcon(fotoBytes).getImage()
                    .getScaledInstance(120, 120, Image.SCALE_SMOOTH);
            lblFoto.setIcon(new ImageIcon(img));
            lblFoto.setText("");
        }
    }


    private void guardar() {
        try {
            if (usuario == null) {
                usuario = new Usuario();
            }

            usuario.setTipoDocumento(txtTipo.getText());
            usuario.setDocumento(txtDocumento.getText());
            usuario.setNombres(txtNombres.getText());
            usuario.setApellidos(txtApellidos.getText());
            usuario.setEmail(txtEmail.getText());
            usuario.setFoto(fotoBytes);

            UsuarioDAO dao = new UsuarioDAO();

            if (usuario.getId() == 0) {
                dao.guardar(usuario);
            } else {
                dao.actualizar(usuario);
            }

            dispose();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void seleccionarFoto() {
        try {
            JFileChooser chooser = new JFileChooser();
            int opcion = chooser.showOpenDialog(this);

            if (opcion == JFileChooser.APPROVE_OPTION) {
                File file = chooser.getSelectedFile();
                fotoBytes = Files.readAllBytes(file.toPath());

                Image img = new ImageIcon(fotoBytes).getImage()
                        .getScaledInstance(120, 120, Image.SCALE_SMOOTH);
                lblFoto.setIcon(new ImageIcon(img));
                lblFoto.setText("");

            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void agregarCampo(JPanel panel, GridBagConstraints gbc, int y, String label, JTextField campo) {

        gbc.gridx = 0;
        gbc.gridy = y;
        gbc.weightx = 0;
        panel.add(new JLabel(label), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1;
        panel.add(campo, gbc);
    }

}