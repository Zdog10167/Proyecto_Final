package proyecto;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.InputMismatchException;
import java.util.Scanner;

import excepciones.OpcionInvalidaException;

@SuppressWarnings("serial")
public class Bebida extends Producto {

	boolean gaseoso;
	boolean lacteo;
	String medida_cc;

	public Bebida(String nom, int prec, LocalDate fecCad, String estado, boolean gas, boolean lact, String cc) {
		super(nom, prec, fecCad, estado);
		this.gaseoso = gas;
		this.lacteo = lact;
		this.medida_cc = cc;
	}

	public Bebida(Scanner sc) {
		super(sc);
		int respuesta;
		do {
			try {
				System.out.println("El producto es gaseoso? \n 1. Si \n 2. No");
				respuesta = sc.nextInt();
				if (respuesta == 1) {
					gaseoso = true;
				} else if (respuesta == 2) {
					gaseoso = false;
				} else {
					throw new OpcionInvalidaException();
				}
				if (!gaseoso) {
					System.out.println("El es lacteo? \n 1. Si \n 2. No");
					respuesta = sc.nextInt();
					if (respuesta == 1) {
						lacteo = true;
					} else if (respuesta == 2) {
						lacteo = false;
					} else {
						throw new OpcionInvalidaException();
					}
				}
				System.out.println("Medida en CC (Centimetros cubicos)");
				medida_cc = sc.next();
				System.out.println("Fecha de envasado (DD-MM-AAAA): ");
				String fechaEnvasado = sc.next();
				comprobarBebida();
				setFechaEnvasado(sc, fechaEnvasado);
				comprobarEstado();
			} catch (InputMismatchException e) {
				System.err.println("Error: Valor prohibido");
				sc.next();
				productoValido = false;
			} catch (OpcionInvalidaException e) {
				System.out.println(e.getMessage());
			}
		} while (!productoValido);

	}

	@Override
	public LocalDate obtenerCaducidad() {
		if (lacteo) {
			fecha_Caducidad = fecha_Envasado.plusDays(10);
		} else {
			fecha_Caducidad = fecha_Envasado.plusDays(20);
		}
		return fecha_Caducidad;
	}

	@Override
	public void detalle_Producto() {
		detallesBasicos_Producto();
		if (gaseoso) {
			System.out.println("Producto gaseoso");
		}
		if (lacteo) {
			System.out.println("Producto lacteo");
		}
		System.out.println("Medida en CC: " + medida_cc + "\nFecha de caducidad: " + fecha_Caducidad);
	}

	public void comprobarBebida() {
		if (medida_cc != null) {
			productoValido = true;
		} else {
			productoValido = false;
		}
	}

	@Override
	public void setFechaEnvasado(Scanner sc, String fecha) {
		boolean fin = false;
		while (!fin) {
			try {
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

				fecha_Envasado = LocalDate.parse(fecha, formatter);
				if (lacteo) {
					fecha_Caducidad = fecha_Envasado.plusDays(10);
				} else {
					fecha_Caducidad = fecha_Envasado.plusDays(20);
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

}
