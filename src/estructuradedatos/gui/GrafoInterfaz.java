package estructuradedatos.gui;

import estructuradedatos.grafo.Arista;
import estructuradedatos.grafo.Vertice;

/**
 * Interfaz que sirve para controlar mostrar en controles los cambios que
 * ocurren con el manejador del grafo
 * 
 * @author Pablo Herrera
 *
 */
public interface GrafoInterfaz {

	/**
	 * Se ejecuta para solicitar el nombre de un nuevo vértice. Si se devuelve null,
	 * el vértice se autonombrará
	 * 
	 * @return Nombre del nuevo vértice
	 */
	String obtenerNombreVertice();

	/**
	 * Sirve para mostrar un error al usuario
	 * 
	 * @param mensaje Mensaje del error
	 */
	void mostrarError(String mensaje);

	/**
	 * Se ejecuta cuando se selecciona un vértice en el grafo
	 * 
	 * @param v Vértice seleccionado
	 */
	void verticeSeleccionado(Vertice v);

	/**
	 * Se ejecuta cuando se selecciona una arista en el grafo
	 * 
	 * @param a Arista seleccionada
	 */
	void aristaSeleccionada(Arista a);

	/**
	 * Se ejecuta cuando el usuario no selecciona ni un vértice ni una arista
	 */
	void seleccionaNada();

	/**
	 * Se usa para preguntar al usuario si desea eliminar una arista. Si la
	 * respuesta es true, se elimina
	 * 
	 * @param a Arista a eliminar
	 * @return true para eliminarla
	 */
	boolean eliminarArista(Arista a);

	/**
	 * Se usa para preguntar al usuario si desea eliminar un vértice. Si la
	 * respuesta es true, se elimina
	 * 
	 * @param v Vértice
	 * @return true para eliminarlo
	 */
	boolean eliminarVertice(Vertice v);

	/**
	 * Se obtiene la ruta mínima
	 */
	void obtieneRutaMinima();

	/**
	 * Se selecciona uno de los vértices para la ruta mínima
	 * 
	 * @param v       Vértice
	 * @param inicial true si es el inicial
	 */
	void obtieneVerticeRutaMinima(Vertice v, boolean inicial);

}
