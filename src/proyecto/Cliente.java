package proyecto;

import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import conexiones_BD_Ficheros.Ficheros;
import excepciones.ClienteInvalidoException;
import excepciones.TelefonoInvalidoException;

public class Cliente implements Serializable {
	// TODO: Eliminar todo lo relativo a ficheros y adaptar a la nueva práctica

	/**
	 * 
	 */
	private static final long serialVersionUID = -4411119939495580730L;
	// Datos del cliente
	public String nombre;
	public String apellido;
	public String telefono;
	public String direccion;
	public String historial;
	public LocalDate fechaDeAlta;

	boolean clienteValido;

	// Constructor de Cliente
	public Cliente(Scanner sc) {
		sc.nextLine();
		while (!clienteValido) {
			try {
				if (getNombre() == null) {
					System.out.println("Nombre:");
					nombre = sc.nextLine();
				}
				if (apellido == null) {
					System.out.println("Apellido:");
					apellido = sc.nextLine();
				}
				if (telefono == null) {
					System.out.println("Telefono:");
					telefono = sc.nextLine();
					formatoTelefonoValido();
				}
				System.out.println("Direccion:");
				direccion = sc.nextLine();
				comprobarCliente();
				if (clienteValido == false) {
					throw new ClienteInvalidoException();
				}
			} catch (InputMismatchException e) {
				// En caso de que se introduzca una letra o simbolo
				System.err.println("Error: Valor prohibido");
				sc.next();
			} catch (ClienteInvalidoException e) {
				// Si el cliente es inválido suelta este exception
				System.err.println(e.getMessage());
			} catch (TelefonoInvalidoException e) {
				System.err.println(e.getMessage());
				telefono = null;
			}
			fechaDeAlta = LocalDate.now();
		}
	}
	
	public Cliente (String nombre, String apellido, String telefono, String direccion, String historial, LocalDate fechaAlta) {
		this.nombre = nombre;
		this.apellido = apellido;
		this.telefono = telefono;
		this.direccion = direccion;
		this.historial = historial;
		this.fechaDeAlta = fechaAlta;
	}

	// Agregar historial al cliente, en caso de ser null lo convierte en ""
	// TODO: Mejorar
	public Cliente agregarHistorial(Cliente cliente, String codigo) {
		if (isValid(historial) == false) {
			historial = "";
		}
		cliente.historial = cliente.historial.concat(codigo);
		return cliente;
	}

	// TODO: Modificar
	public void agregarPedido() {

	}

	public void mostrarCliente() {
		System.out.println("\n\n\n Datos de " + getNombre() + ": \n");

		System.out.println("Nombre: " + getNombre());
		System.out.println("Apellido: " + apellido);
		System.out.println("Teléfono: " + telefono);
		System.out.println("Dirección: " + direccion);
		System.out.println("Fecha de alta: " + fechaDeAlta);
	}

	public static void mostrarClientes(ArrayList<Cliente> clientes) {
		if (clientes.size() == 0) {
			System.err.println("No hay clientes");
		}

		for (int i = 0; i < clientes.size(); i++) {
			clientes.get(i).mostrarCliente();
		}
	}
	
	// Método que comprueba que el teléfono solo tenga números, empiece por 6 7 8 o 9, y mida 9 números de largo
	public boolean formatoTelefonoValido() throws TelefonoInvalidoException {
		if ((telefono.length() == 9 && telefono.matches("[0-9]+") && telefono.matches("[6-9].*")) && !telefonoRepetido()) {
			return true;
		} else {
			throw new TelefonoInvalidoException("Formato incorrecto");
		}
	}

	// Método para comprobar que un String no sea null ni esté en blanco
	public static boolean isValid(String text) {
		if (text == null || text.isBlank()) {
			return false;
		} else {
			return true;
		}
	}

	// Método para comprobar que el cliente es válido, osea, que no tenga ningún hueco vacío
	public void comprobarCliente() throws TelefonoInvalidoException {
		if (isValid(getNombre()) && isValid(apellido) && formatoTelefonoValido() && isValid(direccion)) {
			clienteValido = true;
		} else {
			clienteValido = false;
		}
	}
	
	// Recorre el arrayList comprobando que el teléfono no se repita en ningún cliente
	public boolean telefonoRepetido() throws TelefonoInvalidoException {
		ArrayList<Cliente> clientes = new ArrayList<>();
		try {
			Ficheros.leerClientes(clientes);
		} catch (IOException e) {
			System.err.println("Error: No se pudo comprobar el teléfono repetido");
			e.printStackTrace();
		}

		for (int i = 0; i < clientes.size(); i++) {
			if ((telefono.equals(clientes.get(i).telefono))) {
				throw new TelefonoInvalidoException("Telefono repetido");
			}
		}
		return false;
	}

	public String getNombre() {
		return nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public String getTelefono() {
		return telefono;
	}
}
