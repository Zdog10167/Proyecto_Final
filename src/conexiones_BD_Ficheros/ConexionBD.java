package conexiones_BD_Ficheros;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;

import com.mysql.jdbc.Connection;

import proyecto.Cliente;
import proyecto.Producto;

public class ConexionBD {

	private static final String CONTROLADOR = "com.mysql.jdbc.Driver";
	private static final String URL = "jdbc:mysql://localhost:3306/bd_prueba?characterEncoding=utf8";
	private static final String USUARIO = "root";
	private static final String CLAVE = "password";

	private static String selectTableSQL;
	private static String insertTableSQL;
	private static String updateTableSQL;
	private static String deleteTableSQL;

	static {
		try {
			Class.forName(CONTROLADOR);
		} catch (ClassNotFoundException e) {
			System.out.println("Error al cargar el controlador");
			e.printStackTrace();
		}
	}

	public static LocalDate transformarDate(Date date) {
		LocalDate fechaLocalDate;

		String pattern = "MM/dd/yyyy";
		DateFormat df = new SimpleDateFormat(pattern);
		String fechaDate = df.format(date);
		fechaDate = fechaDate.replace("/", "-");
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");
		fechaLocalDate = LocalDate.parse(fechaDate, formatter);

		return fechaLocalDate;
	}

	public static java.sql.Date transformarLocalDate(LocalDate localDate) {
		java.sql.Date sqlDate = java.sql.Date.valueOf(LocalDate.now());
		return sqlDate;
	}

	public static Connection conseguirConexion() {
		Connection conexion = null;

		try {
			conexion = (Connection) DriverManager.getConnection(URL, USUARIO, CLAVE);
		} catch (SQLException e) {
				System.out.println("Error en la conexion");
				e.printStackTrace();
			}
			return conexion;
	}

	public static void pruebaConectar() {
		try {
			DriverManager.getConnection(URL, USUARIO, CLAVE);

			System.out.println("Conexión establecida con BD correctamente");

		} catch (SQLException e) {
			System.out.println("Error en la conexion");
			e.printStackTrace();
		}
	}

	public static void borrarDatos(String nombreTabla) {

		Connection cn = null;
		PreparedStatement ps = null;

		try {

			cn = conseguirConexion();
			deleteTableSQL = "delete from " + nombreTabla + ";";
			ps = cn.prepareStatement(deleteTableSQL);

			ps.executeQuery("SET SQL_SAFE_UPDATES=0;");
			ps.executeUpdate();


		} catch (SQLException e) {
			e.printStackTrace();

		} finally { // Liberar recursos revisar el orden en el que se cierran
			try {

				if (ps != null) {
					ps.close();
				}

				if (cn != null) {
					cn.close();
				}

			} catch (Exception e2) {

				e2.printStackTrace();

			}
		}
	}

	public static void leerDatos(ArrayList<Cliente> clientesBD, ArrayList<Producto> productosBD) {
		Connection cn = null;
		Statement stm = null;
		ResultSet rs = null;

		// Este try lee los clientes
		try {
			cn = conseguirConexion();
			stm = cn.createStatement();
			rs = stm.executeQuery("Select * from clientes");

			while (rs.next()) {
				clientesBD.add(new Cliente(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),rs.getString(5), transformarDate(rs.getDate(6))));
			}

		} catch (SQLException e) {
			System.out.println("Error al leer la BD en Clientes");
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (stm != null) {
					stm.close();
				}
				if (cn != null) {
					cn.close();
				}
			} catch (Exception e2) {
				System.out.println("Error no identificado");
				e2.printStackTrace();
			}
		}

		// Este try lee los productos
		try {
			cn = conseguirConexion();
			stm = cn.createStatement();
			rs = stm.executeQuery("Select * from comidas");

			while (rs.next()) {
				// TODO: Adaptar a productos
				// clientesBD.add(new Cliente(rs.getString(1), rs.getString(2), rs.getString(3),
				// rs.getString(4),
				// rs.getString(5), fechaAltaLocalDate));
			}

		} catch (SQLException e) {
			System.out.println("Error al leer la BD en Productos");
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (stm != null) {
					stm.close();
				}
				if (cn != null) {
					cn.close();
				}
			} catch (Exception e2) {
				System.out.println("Error no identificado");
				e2.printStackTrace();
			}
		}
	}
	
	public static void escribirDatos(ArrayList<Cliente> clientes, ArrayList<Producto> productos) {
		
		Connection cn = null;
		PreparedStatement ps = null;

		// Crear sentencia SQL para insertar en la base de datos
		insertTableSQL = "INSERT INTO clientes VALUES (?,?,?,?,?,?)";

		try {

			borrarDatos("clientes");

			cn = conseguirConexion();
			ps = cn.prepareStatement(insertTableSQL);

			for (int i = 0; i < clientes.size(); i++) {
				ps.setString(1, clientes.get(i).nombre);
				ps.setString(2, clientes.get(i).apellido);
				ps.setString(3, clientes.get(i).telefono);
				ps.setString(4, clientes.get(i).direccion);
				ps.setString(5, clientes.get(i).historial);
				ps.setDate(6, transformarLocalDate(clientes.get(i).fechaDeAlta));
				ps.executeUpdate();
			}

			System.out.println("Clientes guardados con exito en la base de datos");

		} catch (SQLException e) {
			System.err.println("Error al guardar los Clientes en la Base de fatos");
			e.printStackTrace();

		} finally { // Liberar recursos revisar el orden en el que se cierran
			try {

				if (ps != null) {
					ps.close();
				}

				if (cn != null) {
					cn.close();
				}

			} catch (Exception e2) {

				e2.printStackTrace();

			}
		}
	}
}
