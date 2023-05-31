package proyecto;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class Ficheros {

	private static String ficheroClientes = "./Ficheros/Clientes.dat";
	private static String ficheroProductos = "./Ficheros/Productos.dat";

	public static void escribir(ArrayList<Cliente> clientes, ArrayList<Producto> productos) {
		try {
			FileOutputStream fos = new FileOutputStream("./Ficheros/Clientes.dat");
			ObjectOutputStream oos = new ObjectOutputStream(fos);

			for (int i = 0; i < clientes.size(); i++) {
				oos.writeObject(clientes.get(i));
			}

			if (oos != null) {
				oos.close();
				fos.close();
			}
		} catch (FileNotFoundException e) {
			System.err.println("Error: No se pudo acceder al fichero Clientes");
		} catch (IOException e) {
			System.err.println("Error: Error al escribir los clientes");
			e.printStackTrace();
		} catch (IndexOutOfBoundsException e) {
			System.err.println("Error: No hay clientes");
		}

		try {
			FileOutputStream fos = new FileOutputStream(ficheroProductos);
			ObjectOutputStream oos = new ObjectOutputStream(fos);

			for (int i = 0; i < productos.size(); i++) {
				oos.writeObject(productos.get(i));
			}

			if (oos != null) {
				oos.close();
				fos.close();
			}
		} catch (FileNotFoundException e) {
			System.err.println("Error: No se pudo acceder al fichero Productos");
		} catch (IOException e) {
			System.err.println("Error: Error al escribir los Productos");
			e.printStackTrace();
		} catch (IndexOutOfBoundsException e) {
			System.err.println("Error: No hay productos");
		}
	}

	public static void escribirClientes(ArrayList<Cliente> clientes) {
		try {
			FileOutputStream fos = new FileOutputStream("./Ficheros/Clientes.dat");
			ObjectOutputStream oos = new ObjectOutputStream(fos);

			for (int i = 0; i < clientes.size(); i++) {
				oos.writeObject(clientes.get(i));
			}

			if (oos != null) {
				oos.close();
				fos.close();
			}

		} catch (FileNotFoundException e) {
			System.err.println("Error: No se pudo acceder al fichero Clientes");
		} catch (IOException e) {
			System.err.println("Error: Error al escribir los clientes");
			e.printStackTrace();
		} catch (IndexOutOfBoundsException e) {
			System.err.println("Error: No hay clientes");
		}
	}

	public static void escribirProductos(ArrayList<Producto> productos) {
		try {
			FileOutputStream fos = new FileOutputStream("./Ficheros/Productos.dat");
			ObjectOutputStream oos = new ObjectOutputStream(fos);

			for (int i = 0; i < productos.size(); i++) {
				oos.writeObject(productos.get(i));
			}

			if (oos != null) {
				oos.close();
				fos.close();
			}
		} catch (FileNotFoundException e) {
			System.err.println("Error: No se pudo acceder al fichero Productos");
		} catch (IOException e) {
			System.err.println("Error: Error al escribir los Productos");
			e.printStackTrace();
		} catch (IndexOutOfBoundsException e) {
			System.err.println("Error: No hay Productos");
		}
	}

	public static void leer(ArrayList<Cliente> clientes, ArrayList<Producto> productos) throws IOException {
		FileInputStream fis = null;
		ObjectInputStream ois = null;

		try {
			fis = new FileInputStream(ficheroClientes);
			ois = new ObjectInputStream(fis);

			while (true) {
				clientes.add((Cliente) ois.readObject());
			}
		} catch (EOFException e) {
			if (clientes.size() == 0) {
				System.err.println("Error: No hay contenido en el fichero Clientes");
			}
		} catch (FileNotFoundException e) {
			System.err.println("Error: fichero de lectura Clientes no encontrado");
		} catch (ClassNotFoundException e) {
			System.err.println("Error: problema al cargar los Clientes");
		} finally {
			if (ois != null) {
				ois.close();
				fis.close();
			}
		}

		FileInputStream fis2 = null;
		ObjectInputStream ois2 = null;

		try {
			fis2 = new FileInputStream(ficheroProductos);
			ois2 = new ObjectInputStream(fis2);

			while (true) {
				productos.add((Producto) ois2.readObject());
			}
		} catch (EOFException e) {
			if (productos.size() == 0) {
				System.err.println("Error: No hay contenido en el fichero Productos");
			}
		} catch (FileNotFoundException e) {
			System.err.println("Error: fichero de lectura Productos no encontrado");
		} catch (ClassNotFoundException e) {
			System.err.println("Error: problema al cargar los Productos");
		} finally {
			if (ois2 != null) {
				ois2.close();
				fis2.close();
			}
			// Con esto los productos que al agregarse no estaban caducados pero al momento
			// de cargarlos si lo están se le cambiará el estado
			Producto.comprobarEstadoProductos(productos);
		}
	}

	public static void leer2(ArrayList<Cliente> clientes, ArrayList<Producto> productos) throws IOException {
		FileInputStream fis = null;
		ObjectInputStream ois = null;

		try {
			fis = new FileInputStream(ficheroClientes);
			ois = new ObjectInputStream(fis);

			while (true) {
				clientes.add((Cliente) ois.readObject());
			}
		} catch (EOFException e) {
		} catch (FileNotFoundException e) {
			System.err.println("Error: fichero de lectura Clientes no encontrado");
		} catch (ClassNotFoundException e) {
			System.err.println("Error: problema al cargar los Clientes");
		} finally {
			if (ois != null) {
				ois.close();
				fis.close();
			}
		}

		FileInputStream fis2 = null;
		ObjectInputStream ois2 = null;

		try {
			fis2 = new FileInputStream(ficheroProductos);
			ois2 = new ObjectInputStream(fis2);

			while (true) {
				productos.add((Producto) ois2.readObject());
			}
		} catch (EOFException e) {
		} catch (FileNotFoundException e) {
			System.err.println("Error: fichero de lectura Productos no encontrado");
		} catch (ClassNotFoundException e) {
			System.err.println("Error: problema al cargar los Productos");
		} finally {
			if (ois2 != null) {
				ois2.close();
				fis2.close();
			}
		}
	}

	public static void leerClientes(ArrayList<Cliente> clientes) throws IOException {
		FileInputStream fis = null;
		ObjectInputStream ois = null;

		try {
			fis = new FileInputStream(ficheroClientes);
			ois = new ObjectInputStream(fis);

			while (true) {
				clientes.add((Cliente) ois.readObject());
			}
		} catch (EOFException e) {
		} catch (FileNotFoundException e) {
			System.err.println("Error: fichero de lectura Clientes no encontrado");
		} catch (ClassNotFoundException e) {
			System.err.println("Error: problema al cargar los Clientes");
		} finally {
			if (ois != null) {
				ois.close();
				fis.close();
			}
		}
	}

	public static void leerProductos(ArrayList<Producto> productos) throws IOException {
		FileInputStream fis = null;
		ObjectInputStream ois = null;

		try {
			fis = new FileInputStream(ficheroProductos);
			ois = new ObjectInputStream(fis);

			while (true) {
				productos.add((Producto) ois.readObject());
			}
		} catch (EOFException e) {
			if (productos.size() == 0) {
				System.err.println("Error: No hay contenido en el fichero Productos");
			}
		} catch (FileNotFoundException e) {
			System.err.println("Error: fichero de lectura Productos no encontrado");
		} catch (ClassNotFoundException e) {
			System.err.println("Error: problema al cargar los Productos");
		} finally {
			if (ois != null) {
				ois.close();
				fis.close();
			}
			Producto.comprobarEstadoProductos(productos);
		}
	}
}
