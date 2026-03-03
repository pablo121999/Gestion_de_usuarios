package dao;

import model.Usuario;
import util.ConexionDB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {

    public void guardar(Usuario u) throws Exception {
        String sql = "INSERT INTO usuarios (tipo_documento, documento, nombres, apellidos, email, foto) VALUES (?,?,?,?,?,?)";

        try (Connection con = ConexionDB.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, u.getTipoDocumento());
            ps.setString(2, u.getDocumento());
            ps.setString(3, u.getNombres());
            ps.setString(4, u.getApellidos());
            ps.setString(5, u.getEmail());
            ps.setBytes(6, u.getFoto());

            ps.executeUpdate();
        }
    }

    public List<Usuario> listar() throws Exception {
        List<Usuario> lista = new ArrayList<>();
        String sql = "SELECT * FROM usuarios";

        try (Connection con = ConexionDB.conectar();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                Usuario u = new Usuario();
                u.setId(rs.getInt("id"));
                u.setTipoDocumento(rs.getString("tipo_documento"));
                u.setDocumento(rs.getString("documento"));
                u.setNombres(rs.getString("nombres"));
                u.setApellidos(rs.getString("apellidos"));
                u.setEmail(rs.getString("email"));
                u.setFoto(rs.getBytes("foto"));

                lista.add(u);
            }
        }
        return lista;
    }

    public void eliminar(int id) throws Exception {
        String sql = "DELETE FROM usuarios WHERE id=?";

        try (Connection con = ConexionDB.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    public void actualizar(Usuario u) throws Exception {
        String sql = "UPDATE usuarios SET tipo_documento=?, documento=?, nombres=?, apellidos=?, email=?, foto=? WHERE id=?";

        try (Connection con = ConexionDB.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, u.getTipoDocumento());
            ps.setString(2, u.getDocumento());
            ps.setString(3, u.getNombres());
            ps.setString(4, u.getApellidos());
            ps.setString(5, u.getEmail());
            ps.setBytes(6, u.getFoto());
            ps.setInt(7, u.getId());

            ps.executeUpdate();
        }
    }


    public Usuario buscarPorId(int id) {
        Usuario usuario = null;

        try {
            String sql = "SELECT * FROM usuarios WHERE id = ?";
            var conn = ConexionDB.conectar();
            var ps = conn.prepareStatement(sql);
            ps.setInt(1, id);

            var rs = ps.executeQuery();

            if (rs.next()) {
                usuario = new Usuario();
                usuario.setId(rs.getInt("id"));
                usuario.setTipoDocumento(rs.getString("tipo_documento"));
                usuario.setDocumento(rs.getString("documento"));
                usuario.setNombres(rs.getString("nombres"));
                usuario.setApellidos(rs.getString("apellidos"));
                usuario.setEmail(rs.getString("email"));
                usuario.setFoto(rs.getBytes("foto"));
            }

            rs.close();
            ps.close();
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return usuario;
    }

}