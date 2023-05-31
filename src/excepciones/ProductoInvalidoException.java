package excepciones;

public class ProductoInvalidoException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String mensaje = "Error: Producto inválido";
	private static final String mensajeC = "Error: Producto Inválido. Causa: ";

	public ProductoInvalidoException() {
		super(mensaje);
	}

	public ProductoInvalidoException(String causa) {
		super(mensajeC + causa);
	}
}
