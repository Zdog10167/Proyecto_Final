package proyecto;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import excepciones.ProductoInvalidoException;

public abstract class Producto implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8515567495471734884L;

	boolean productoValido = false;

	String nombre;
	double precio = 0;
	int[] stock = new int[30];

	LocalDate fecha_Caducidad;
	LocalDate fecha_Envasado = LocalDate.now();
	String estado = "Buen estado";

	public static void comprobarEstadoProductos(ArrayList<Producto> productos) {
		for (int i = 0; i < productos.size(); i++) {
			Producto p = productos.get(i);
			p.comprobarEstado();
		}
	}

	public void comprobarEstado() {
		if (fecha_Caducidad.isBefore(LocalDate.now().plusDays(5))) {
			estado = "Cerca de caducar";
		}
		if (fecha_Caducidad.isBefore(LocalDate.now())) {
			estado = "Caducado";
		}
	}

	public static double precioDescontado(double precioSinCortar) {
		precioSinCortar *= 0.7;
		BigDecimal precioCortado = new BigDecimal(precioSinCortar).setScale(2, RoundingMode.DOWN);
		double newPrecio = precioCortado.doubleValue();
		return newPrecio;
	}

	public static ArrayList<Producto> cambiarOrdenArrayList(ArrayList<Producto> productos) {
		ArrayList<Producto> nuevoOrden = new ArrayList<>();
		int index = 0;

		for (int i = 0; i < productos.size(); i++) {
			if (productos.get(i).estado.equals("Caducado")) {
				nuevoOrden.add(productos.get(i));
			} else {
				nuevoOrden.add(index, productos.get(i));
				index++;
			}
		}
		productos = nuevoOrden;
		return productos;
	}

	public Producto(Scanner sc) {
		sc.nextLine();
		do {
			try {
				if (nombre == null || nombre == "") {
					System.out.println("\nNombre:");
					nombre = sc.nextLine();
					if (productoRepetido()) {
						throw new ProductoInvalidoException("Producto ya existente");
					}
				}
				if (precio == 0) {
					System.out.println("Precio:");
					precio = sc.nextDouble();
					BigDecimal precioCortado = new BigDecimal(precio).setScale(2, RoundingMode.DOWN);
					precio = precioCortado.doubleValue();

				}
				if (stock[0] != 1) {
					System.out.println("Número de Stock (Minimo 1 y Maximo 30):");
					agregarStock(sc.nextInt());
				}
				comprobarProducto();
			} catch (InputMismatchException e) {
				System.err.println("Error: Valor prohibido");
				sc.next();
				productoValido = false;
			} catch (ProductoInvalidoException e) {
				System.err.println(e.getMessage());
				nombre = "";
				productoValido = false;
			}
		} while (!productoValido);
	}

	public Producto(String nom, double prec, LocalDate fecCad, String estado) {
		this.nombre = nom;
		this.precio = prec;
		this.fecha_Caducidad = fecCad;
		this.estado = estado;
	}

	public abstract LocalDate obtenerCaducidad();

	public abstract void detalle_Producto();

	public void detallesBasicos_Producto() {
		System.out.println("\n\nNombre: " + nombre + "\nPrecio: " + precio + "\nStock: " + contarStock());
		System.out.println(estado);
	};

	private void comprobarProducto() throws ProductoInvalidoException {
		if (((nombre != null && nombre != "") && precio != 0 && estado != null) && !productoRepetido()) {
			productoValido = true;
		} else {
			productoValido = false;
			System.err.println("Producto inválido");
		}
	}

	private boolean productoRepetido() throws ProductoInvalidoException {
		ArrayList<Producto> productos = new ArrayList<>();
		try {
			Ficheros.leerProductos(productos);
		} catch (IOException e) {
			System.err.println("Error: No se pudo comprobar el teléfono repetido");
			e.printStackTrace();
		}

		for (int i = 0; i < productos.size(); i++) {
			if ((nombre.equals(productos.get(i).nombre))) {
				throw new ProductoInvalidoException("Nombre de producto repetido");
			}
		}
		return false;
	}

	public void agregarStock(int numStock) {
		if (numStock > 30) {
			System.err.println("Demasiado stock, se limitará a 30");
			numStock = 30;
		}
		if (numStock < 1) {
			System.err.println("No se pueden meter 0 de stock, se agregará 1");
			numStock = 1;
		}
		for (int i = 0; i < numStock; i++) {
			stock[i] = 1;
		}
	}

	public void restarStock() {
		int i = 0;
		while (stock[i] == 1) {
			i++;
		}
		stock[i - 1] = 0;
	}

	public int contarStock() {
		int numStock = 0;
		for (int i = 0; i < stock.length; i++) {
			if (stock[i] == 1) {
				numStock++;
			}
		}
		return numStock;
	}

	public void setFechaCad(Scanner sc, String fecha) {
		boolean fin = false;
		while (!fin) {
			try {
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
				fecha_Caducidad = LocalDate.parse(fecha, formatter);
				fin = true;
			} catch (Exception e) {
				System.err.println("Error: Formato o fecha incorrectos, vuelve a escribirlo");
				fecha_Caducidad = null;
				System.out.println("Fecha de caducidad (DD-MM-AAAA): ");
				fecha = null;
				fecha = sc.next();
			}
		}
	}
	
	public abstract void setFechaEnvasado(Scanner sc, String fecha);

	public static void mostrarProductos(ArrayList<Producto> productos) {
		if (productos.size() == 0) {
			System.err.println("No hay productos");
		}

		for (int i = 0; i < productos.size(); i++) {
			productos.get(i).detalle_Producto();
		}
	}

}
