package estructuradedatos.gui;

/**
 * Interfaz que sirve para mostrar el estado del manejador de grafos
 * 
 * @author Pablo Herrera
 *
 */
public interface GrafoEstadoListener {

	/**
	 * Se ejecuta cuando cambia el estado del manejador de grafos
	 * 
	 * @param estadoAnterior Estado anterior
	 * @param estadoActual   Estado actual
	 */
	void cambiaEstado(int estadoAnterior, int estadoActual);

}
