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
	 * Se ejecuta para solicitar el nombre de un nuevo v�rtice. Si se devuelve null,
	 * el v�rtice se autonombrar�
	 * 
	 * @return Nombre del nuevo v�rtice
	 */
	String obtenerNombreVertice();

	/**
	 * Sirve para mostrar un error al usuario
	 * 
	 * @param mensaje Mensaje del error
	 */
	void mostrarError(String mensaje);

	/**
	 * Se ejecuta cuando se selecciona un v�rtice en el grafo
	 * 
	 * @param v V�rtice seleccionado
	 */
	void verticeSeleccionado(Vertice v);

	/**
	 * Se ejecuta cuando se selecciona una arista en el grafo
	 * 
	 * @param a Arista seleccionada
	 */
	void aristaSeleccionada(Arista a);

	/**
	 * Se ejecuta cuando el usuario no selecciona ni un v�rtice ni una arista
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
	 * Se usa para preguntar al usuario si desea eliminar un v�rtice. Si la
	 * respuesta es true, se elimina
	 * 
	 * @param v V�rtice
	 * @return true para eliminarlo
	 */
	boolean eliminarVertice(Vertice v);

	/**
	 * Se obtiene la ruta m�nima
	 */
	void obtieneRutaMinima();

	/**
	 * Se selecciona uno de los v�rtices para la ruta m�nima
	 * 
	 * @param v       V�rtice
	 * @param inicial true si es el inicial
	 */
	void obtieneVerticeRutaMinima(Vertice v, boolean inicial);

}
