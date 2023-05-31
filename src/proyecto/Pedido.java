package proyecto;

import java.util.ArrayList;

import excepciones.ProductoInvalidoException;

public class Pedido {

	Cliente clientePedido;
	ArrayList<Producto> productos = new ArrayList<>();

	public Pedido(ArrayList<Cliente> clientes, ArrayList<Producto> productos) {
		this.productos = productos;

	}

	public static void mostrarClientesPedido(ArrayList<Cliente> clientes) {
		System.out.println("\n");
		for (int i = 0; i < clientes.size(); i++) {
			System.out.println("Cliente " + (i + 1) + " " + clientes.get(i).nombre + ": " + clientes.get(i).telefono);
		}
	}

	public static void mostrarProductosPedido(ArrayList<Producto> productos) {
		productos = Producto.cambiarOrdenArrayList(productos);
		for (int i = 0; i < productos.size(); i++) {
			if (!productos.get(i).estado.equals("Caducado")) {
				System.out.println("Producto " + (i + 1) + ": " + productos.get(i).nombre);
				System.out.println("Precio: " + productos.get(i).precio);
				System.out.println("Estado: " + productos.get(i).estado);
				System.out.println("Stock: " + productos.get(i).contarStock());
				if (productos.get(i).estado.equals("Cerca de caducar")) {
					System.out.println("DESCUENTO POR CADUCIDAD PRÓXIMA: 30%");
					System.out.println("Nuevo precio: " + Producto.precioDescontado(productos.get(i).precio));
				}
				System.out.println();
			}
		}
	}

	public static Producto elegirProducto(ArrayList<Producto> productos, int numero)
			throws ProductoInvalidoException {
		int numStock = productos.get(numero - 1).contarStock();
		if (numStock != 0) {
			System.out.println("Agregado el producto " + productos.get(numero).nombre);
			productos.get(numero).restarStock();
			return productos.get(numero);
		} else {
			throw new ProductoInvalidoException("El Producto seleccionado no tiene Stock");

		}
	}

	public static void listaCompra(ArrayList<Producto> listaProductos) {
		for (int i = 0; i < listaProductos.size(); i++) {
			System.out.print(listaProductos.get(i).nombre + " ");
		}
	}
}
