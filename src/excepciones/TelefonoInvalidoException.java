package excepciones;

public class TelefonoInvalidoException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static String mensaje = "Error: Telefono inválido, causa: ";

	public TelefonoInvalidoException(String causa) {
		super(mensaje + causa);
	}

}
