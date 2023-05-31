package excepciones;

public class OpcionInvalidaException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String mensaje = "Error: Opcion inválida";

	public OpcionInvalidaException() {
		super(mensaje);
	}
}
