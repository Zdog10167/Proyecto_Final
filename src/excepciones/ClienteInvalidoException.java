package excepciones;

public class ClienteInvalidoException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static String mensaje = "Error: Cliente inválido";

	public ClienteInvalidoException() {
		super(mensaje);
	}

}
