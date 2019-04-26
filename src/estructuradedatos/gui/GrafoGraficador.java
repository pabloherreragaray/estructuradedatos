package estructuradedatos.gui;

/**
 * Interfaz que sirve para graficar el grafo
 * 
 * @author Pablo Hererra
 *
 */
public interface GrafoGraficador {

	/**
	 * Se ejecuta cuando cambia la dimensión del grafo
	 * 
	 * @param ancho Nuevo ancho del grafo
	 * @param alto  Nuevo alto del grafo
	 */
	void setDimension(int ancho, int alto);

	/**
	 * Se ejecuta cuando se debe graficar el grafo nuevamente, porque hubo un cambio
	 */
	void actualizar();

}
