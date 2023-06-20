package jUnits;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Scanner;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import proyecto.Comida;
import proyecto.Producto;

public class StockTest {
	private Producto producto;

	@BeforeEach
	public void setUp() {
		// No se puede instanciar Producto directamente, pero como el Stock funciona
		// igual para ambos se instancia una comida y ya
		producto = new Comida(new Scanner(System.in));
	}

	@Test
	public void agregarStock_NumStockMayorA30_LimitaA30() {
		producto.agregarStock(35);
		assertEquals(30, producto.contarStock());
	}

	@Test
	public void agregarStock_NumStockMenorA1_Agrega1() {
		producto.agregarStock(0);
		assertEquals(1, producto.contarStock());
	}

	@Test
	public void agregarStock_NumStockValido_AgregaCorrectamente() {
		producto.agregarStock(15);
		assertEquals(15, producto.contarStock());
	}

	@Test
	public void restarStock_StockVacio_NoRestaNada() {
		producto.restarStock();
		assertEquals(0, producto.contarStock());
	}

	@Test
	public void restarStock_StockConElementos_RestaUno() {
		producto.agregarStock(10);
		producto.restarStock();
		assertEquals(9, producto.contarStock());
	}

	@Test
	public void contarStock_StockVacio_RetornaCero() {
		assertEquals(0, producto.contarStock());
	}

	@Test
	public void contarStock_StockConElementos_RetornaCantidadCorrecta() {
		producto.agregarStock(20);
		assertEquals(20, producto.contarStock());
	}
}
