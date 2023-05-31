package proyecto;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.InputMismatchException;
import java.util.Scanner;

import excepciones.OpcionInvalidaException;

@SuppressWarnings("serial")
public class Comida extends Producto {

	// Atributos Comida
	boolean perecedero;
	float calorias;
	boolean vegano;

	// Constructor
	public Comida(String nombre, int precio, LocalDate fecha_Caducidad, String estado, boolean perecedero, float calorias, boolean vegano, LocalDate fecha_Envasado) {
		super(nombre, precio, fecha_Caducidad, estado);
		
		this.perecedero = perecedero;
		this.calorias = calorias;
		this.vegano = vegano;
		this.fecha_Envasado = fecha_Envasado;

		obtenerCaducidad();
	}

	public Comida(Scanner sc) {
		super(sc);
		int respuesta;
		do {
			try {
				System.out.println("El producto es perecedero? \n 1. Si \n 2. No");
				respuesta = sc.nextInt();
				System.out.println("Fecha de envasado (DD-MM-AAAA): ");
				String fechaEnvasado = sc.next();
				if (respuesta == 1) {
					// Si el producto es perecedero la fecha de caducidad será 10 días después a la de envasado
					perecedero = true;
					setFechaEnvasado(sc, fechaEnvasado);
				} else if (respuesta == 2) {
					perecedero = false;
					// Si no es perecedero se preguntarán ambos
					setFechaEnvasado(sc, fechaEnvasado);
					System.out.println("Fecha de caducidad (DD-MM-AAAA): ");
					String fechaCad = sc.next();
					setFechaCad(sc, fechaCad);
				} else {
					throw new OpcionInvalidaException();
				}

				System.out.println("Cuantas calorias tiene?");
				calorias = sc.nextFloat();

				System.out.println("El producto es vegano? \n 1. Si \n 2. No");
				respuesta = sc.nextInt();
				if (respuesta == 1) {
					vegano = true;
				} else if (respuesta == 2) {
					vegano = false;
				} else {
					throw new OpcionInvalidaException();
				}
				fecha_Caducidad = obtenerCaducidad();
				comprobarEstado();
				comprobarProducto();
			} catch (InputMismatchException e) {
				System.err.println("Error: Valor prohibido");
				sc.next();
				productoValido = false;
			} catch (OpcionInvalidaException e) {
				System.out.println(e.getMessage());
				productoValido = false;
			}
		} while (!productoValido);

	}

	public void comprobarProducto() {
		if (calorias != 0 && fecha_Envasado != null) {
			productoValido = true;
		} else {
			productoValido = false;
			System.err.println("Producto inválido");
		}
	}

	// Retorna la fecha de caducidad del producto, si es perecedero la fecha será 10
	// días después de la fecha de envasado
	@Override
	public LocalDate obtenerCaducidad() {
		if (perecedero) {
			fecha_Caducidad = fecha_Envasado.plusDays(10);
		}
		return fecha_Caducidad;
	}
	
	@Override
	public void setFechaEnvasado(Scanner sc, String fecha) {
		boolean fin = false;
		while (!fin) {
			try {
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

				fecha_Envasado = LocalDate.parse(fecha, formatter);
				if (perecedero) {
					fecha_Caducidad = fecha_Envasado.plusDays(10);
				}
				fin = true;
			} catch (Exception e) {
				System.err.println("Error: Formato o fecha incorrectos, vuelve a escribirlo");
				fecha_Caducidad = null;
				System.out.println("Fecha de envasado (DD-MM-AAAA): ");
				fecha = null;
				fecha = sc.next();
			}
		}
	}

	// Detalles de la comida
	@Override
	public void detalle_Producto() {
		detallesBasicos_Producto();
		if (perecedero) {
			System.out.println("Producto perecedero");
		}
		if (vegano) {
			System.out.println("Producto vegano");
		}
		System.out.println("Calorias: " + calorias);
		System.out.println("Fecha de envasado: " + fecha_Envasado);
		System.out.println("Fecha de caducidad: " + fecha_Caducidad);
	}
}
