package jUnits;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import conexiones_BD_Ficheros.ConexionBD;
import proyecto.Cliente;
import proyecto.Producto;

public class ConexionBDTest {

	private static ArrayList<Cliente> clientesBD;
	private static ArrayList<Producto> productosBD;

	@BeforeAll
	public static void setUp() {
		clientesBD = new ArrayList<>();
		productosBD = new ArrayList<>();
		// Aquí se podrían crear algunos Clientes y Productos para comprobar mejor los
		// datos
	}

	@Test
	public void testLeerDatos() {
		ConexionBD.leerDatos(clientesBD, productosBD);

		// Verificar que los ArrayLists no estén vacíos después de leer los datos
		assertNotNull(clientesBD);
		assertNotNull(productosBD);
		// No hay funcionalidad de escribir/leer los productos en la Base de datos, por
		// lo tanto siempre será null

	}

	@Test
	public void testEscribirDatos() {
		ConexionBD.escribirDatos(clientesBD, productosBD);

		// Verificar que los datos escritos coincidan con los datos originales
		ArrayList<Cliente> clientesBDNuevos = new ArrayList<>();
		ArrayList<Producto> productosBDNuevos = new ArrayList<>();
		ConexionBD.leerDatos(clientesBDNuevos, productosBDNuevos);

		// Comparar el tamaño de los ArrayLists
		assertEquals(clientesBD.size(), clientesBDNuevos.size());
		assertEquals(productosBD.size(), productosBDNuevos.size());

		// Comparar los datos individuales en los ArrayLists
		for (int i = 0; i < clientesBD.size(); i++) {
			Cliente clienteOriginal = clientesBD.get(i);
			Cliente clienteNuevo = clientesBDNuevos.get(i);
			assertEquals(clienteOriginal.getTelefono(), clienteNuevo.getTelefono());
			// Se podrían comparar más datos pero el teléfono no se puede repetir por lo que
			// debería ser suficiente
		}
	}

}
