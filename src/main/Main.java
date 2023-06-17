package main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import conexiones_BD_Ficheros.ConexionBD;
import conexiones_BD_Ficheros.Ficheros;
import excepciones.ClienteInvalidoException;
import excepciones.OpcionInvalidaException;
import proyecto.Cliente;
import proyecto.Menus;
import proyecto.Producto;

public class Main {
	public static void main(String[] args) throws IOException {
		Scanner sc = new Scanner(System.in);
		ConexionBD.pruebaConectar();

		// ArrayLists de clientes y productos
		ArrayList<Cliente> clientes = new ArrayList<>();
		ArrayList<Producto> productos = new ArrayList<>();
		ArrayList<Cliente> clientesBD = new ArrayList<>();
		ArrayList<Producto> productosBD = new ArrayList<>();

		// Se agregan los clientes y los productos de los ficheros con este método
		try {
			Ficheros.leer(clientes, productos);
			ConexionBD.leerDatos(clientesBD, productosBD);
		} catch (Exception e) {
			System.err.println("Error al cargar los datos");
			e.printStackTrace();
		}

		boolean terminar = false;
		while (!terminar) {
			// Se ejecuta el menú inicial hasta que el boolean terminar sea true
			try {
				terminar = (Menus.menuInicial(clientes, clientesBD, productos, productosBD, sc));
			} catch (InputMismatchException e) {
				System.err.println("Error: Valor invalido");
				sc.next();
			} catch (OpcionInvalidaException e) {
				System.err.println(e.getMessage());
			} catch (ClienteInvalidoException e) {
				System.err.println(e.getMessage());
			}
		}

		System.out.println("\nVuelva pronto\n");
		Ficheros.escribir(clientes, productos);
		ConexionBD.escribirDatos(clientes, productos);
		sc.close();
	}
}