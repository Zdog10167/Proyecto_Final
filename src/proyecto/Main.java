package proyecto;

import java.io.IOException;
import java.io.InvalidClassException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import excepciones.ClienteInvalidoException;
import excepciones.OpcionInvalidaException;

public class Main {
	public static void main(String[] args) throws IOException {
		Scanner sc = new Scanner(System.in);

		// ArrayLists de clientes y productos
		ArrayList<Cliente> clientes = new ArrayList<>();
		ArrayList<Producto> productos = new ArrayList<>();

		// Se agregan los clientes y los productos de los ficheros con este método
		try {
			Ficheros.leer(clientes, productos);
		} catch (InvalidClassException e) {
			System.err.println("No se pudo cargar");
			e.printStackTrace();
		}

		boolean terminar = false;
		while (!terminar) {
			// Se ejecuta el menú inicial hasta que el boolean terminar sea true
			try {
				terminar = (Menus.menuInicial(clientes, productos, sc));
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
		sc.close();
	}
}