package util;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConexionDB {

    private static final String URL = "jdbc:mysql://localhost:3306/gestion_usuarios";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    public static Connection conectar() throws Exception {

        Class.forName("com.mysql.cj.jdbc.Driver"); // 🔥 IMPORTANTE

        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}