package proyecto;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import excepciones.ClienteInvalidoException;
import excepciones.OpcionInvalidaException;
import excepciones.ProductoInvalidoException;

public class Menus {
	private static boolean terminar = false;

	public static void enProceso() {
		System.err.println("Esta parte está en proceso");
	}
	
	// Menú inicial
	public static boolean menuInicial(ArrayList<Cliente> clientes, ArrayList<Producto> productos, Scanner sc)
			throws OpcionInvalidaException, InputMismatchException, ClienteInvalidoException {
		int respuesta = 0;

		System.out.println("\n\n ###### Menu inicial ###### \n\n Elige una opción:");
		System.out.println(
				" 1. Mostrar clientes existentes o crear nuevos \n 2. Mostrar productos o crear nuevos \n 3. Realizar un pedido \n 4. Salir y guardar cambios");

		respuesta = 0;
		respuesta = sc.nextInt();

		switch (respuesta) {

		case 1: {
			Menus.clientes(sc, clientes);
		} break;

		case 2: {
			Menus.productos(sc, productos);
		} break;

		case 3: {
			Menus.pedidos(clientes, productos, sc);
		} break;

		case 4:
			return true;

		default:
			throw new OpcionInvalidaException();
		}
		return false;
	}

	// Menú para manipulación de clientes
	public static void clientes(Scanner sc, ArrayList<Cliente> clientes) {
		terminar = false;
		int respuesta;

		while (!terminar) {
			try {
				respuesta = 0;
				System.out.println("\n\n ¿Que accion quieres realizar? \n\n 1. Crear nuevo cliente \n 2. Mostrar los ya creados \n 3. Salir");
				respuesta = sc.nextInt();
				
				switch (respuesta) {
				case 1: {
					clientes.add(new Cliente(sc));
				} break;

				case 2: {
					Cliente.mostrarClientes(clientes);
				} break;

				case 3: {
					terminar = true;
				} break;

				default:
					System.err.println("Error: Opcion invalida");
				}
			} catch (InputMismatchException e) {
				System.err.println("Error: Valor invalido");
				respuesta = 0;
				sc.next();
			}
		}
	}

	// Menú para manipulación de productos
	public static void productos(Scanner sc, ArrayList<Producto> productos) {
		terminar = false;
		int respuesta;

		while (!terminar) {
			try {
				respuesta = 0;
				System.out.println("\n\n ¿Que accion quieres realizar? \n\n 1. Crear nuevo producto \n 2. Mostrar los ya creados \n 3. Salir");
				respuesta = sc.nextInt();

				switch (respuesta) {
				case 1: {
					System.out.println("\nEl producto es comida o es bebida? \n 1. Comida \n 2. Bebida");
					respuesta = sc.nextInt();
					if (respuesta == 1) {
						productos.add(new Comida(sc));
					} else if (respuesta == 2) {
						productos.add(new Bebida(sc));
					} else {
						throw new OpcionInvalidaException();
					}
				} break;

				case 2: {
					Producto.mostrarProductos(productos);
				} break;

				case 3: {
					terminar = true;
				} break;

				default:
					throw new OpcionInvalidaException();
				}
			} catch (InputMismatchException e) {
				System.err.println("Error: Valor invalido");
				respuesta = 0;
				sc.next();
			} catch (OpcionInvalidaException e) {
				System.out.println(e.getMessage());
			}
		}
	}

	// Menú para la realización de pedidos
	public static void pedidos(ArrayList<Cliente> clientes, ArrayList<Producto> productos, Scanner sc) {
		Cliente clientePedido = null;
		ArrayList<Producto> productosPedido = new ArrayList<>();
		terminar = false;

		while (!terminar) {
			try {
				System.out.println("Con qué cliente quieres realizar el pedido? Escriba el número del cliente correspondiente");
				Pedido.mostrarClientesPedido(clientes);
				clientePedido = clientes.get(sc.nextInt() - 1);
				System.out.println("El pedido se realizará con el cliente " + clientePedido.nombre);
				terminar = true;
			} catch (IndexOutOfBoundsException e) {
				System.err.println("Error: Cliente inválido");
			}
		}
		terminar = false;
		while (!terminar) {
			// TODO: Menú pedidos
			try {
				System.out.println("\nElija un producto para agregar la cesta. Escriba el número del producto correspondiente\n");
				Pedido.mostrarProductosPedido(productos);
				productosPedido.add(Pedido.elegirProducto(productos, sc.nextInt()));
				do {
					System.out.println("\nQuieres agregar más productos? \n 1. Si\n 2. No");
					if (sc.nextInt() == 1) {
						System.out.println(
								"\nElija un producto para agregar la cesta. Escriba el número del producto correspondiente\n");
						Pedido.mostrarProductosPedido(productos);
						productosPedido.add(Pedido.elegirProducto(productos, sc.nextInt()));
					} else {
						terminar = true;
					}
				} while (!terminar);

				terminar = false;

				System.out.println("El cliente " + clientePedido.nombre + " va a comprar los siguientes Productos:");
				Pedido.listaCompra(productosPedido);

			} catch (IndexOutOfBoundsException e) {
				System.err.println("Error: Numero inválido");
				terminar = false;
			} catch (ProductoInvalidoException e) {
				System.err.println(e.getMessage());

			} catch (InputMismatchException e) {
				terminar = false;
				System.err.println("Error: Valor invalido");
				sc.next();
			}
		}
	}
}
