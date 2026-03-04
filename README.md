# Sistema de Gestión de Usuarios

Aplicación de escritorio desarrollada en **Java (Swing)** que permite administrar usuarios con almacenamiento en base de datos **MySQL**, incluyendo fotografía.
![img.png](img.png)
---

## 🎯 Objetivo

Desarrollar una aplicación para la gestión de usuarios

---

## 🚀 Funcionalidades

- ✅ Crear usuarios
- ✅ Editar usuarios
- ✅ Eliminar usuarios
- ✅ Listar usuarios en tabla
- ✅ Guardar fotografía en base de datos
- ✅ Mostrar miniatura de fotografía en la tabla
- ✅ Interfaz gráfica amigable
- ✅ Manejo de excepciones

---

## 🛠 Tecnologías Utilizadas

- Java 17+
- Java Swing
- MySQL
- JDBC
- Patrón DAO

---

# ⚙ Configuración del Proyecto

## 1️⃣ Crear Base de Datos MySQL
CREATE DATABASE gestion_usuarios;

USE gestion_usuarios;

CREATE TABLE usuarios (
id INT AUTO_INCREMENT PRIMARY KEY,
tipo_documento VARCHAR(50),
documento VARCHAR(50),
nombres VARCHAR(100),
apellidos VARCHAR(100),
email VARCHAR(100),
foto LONGBLOB
);

## 2️⃣ Configurar conexión a base de datos

Ruta: **src/util/ConexionDB.java**

## 👨‍💻 Autor

Pablo Andres Aroca Garcia