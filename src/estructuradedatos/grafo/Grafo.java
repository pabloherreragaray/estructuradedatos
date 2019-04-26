package estructuradedatos.grafo;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase que representa un grafo
 * 
 * @author Pablo Herrera
 *
 */
public class Grafo {
	private List<Vertice> vertices;
	private List<Arista> aristas;

	/**
	 * Constructor
	 */
	public Grafo() {
		vertices = new ArrayList<Vertice>();
		aristas = new ArrayList<Arista>();
	}

	/**
	 * Devuelve la lista de vértices
	 * 
	 * @return Lista de vértices del grafo
	 */
	public List<Vertice> getVertices() {
		return vertices;
	}

	/**
	 * Devuelve la lista de aristas
	 * 
	 * @return Lista de aristas del vértice
	 */
	public List<Arista> getAristas() {
		return aristas;
	}

	/**
	 * Obtiene un vértice a partir de su nombre
	 * 
	 * @param nombre Nombre del vértice
	 * @return Vértice encontrado o null si no se encontró un vértice
	 */
	public Vertice getVertice(String nombre) {
		Vertice res = null;
		for (Vertice v : vertices) {
			if (v.getNombre().equals(nombre)) {
				res = v;
				break;
			}
		}
		return res;
	}

	/**
	 * Adiciona un vértice existente al grafo
	 * 
	 * @param v Vértice
	 * @return Vértice añadido
	 * @throws Exception Lanzará error si el vértice es null o si el nombre del
	 *                   vértice ya existe
	 */
	public Vertice adicionarVertice(Vertice v) throws Exception {
		if (v == null)
			throw new Exception("El vértice es nulo");
		if (getVertice(v.getNombre()) != null)
			throw new Exception("Ya existe un vértice con el nombre " + v.getNombre());
		vertices.add(v);
		return v;
	}

	/**
	 * Adiciona un vértice pasando el nombre y las coordenadas
	 * 
	 * @param nombre Nombre
	 * @param x      X
	 * @param y      Y
	 * @return Vértice añadido
	 * @throws Exception Lanzará error si el nombre del vértice ya existe
	 */
	public Vertice adicionarVertice(String nombre, double x, double y) throws Exception {
		return adicionarVertice(new Vertice(nombre, x, y));
	}

	/**
	 * Adiciona un vértice pasando solamente sus coordenadas (el nombre se creará
	 * automáticamente)
	 * 
	 * @param x X
	 * @param y Y
	 * @return Vértice añadido
	 * @throws Exception 
	 */
	public Vertice adicionarVertice(double x, double y) throws Exception {
		return adicionarVertice(new Vertice(x, y));
	}

	/**
	 * Obtiene un vértice buscándolo por los nombres de sus vértices extremos
	 * 
	 * @param v1 Nombre del vértice 1
	 * @param v2 Nombre del vértice 2
	 * @return Arista encontrada o null si no se encontró ninguna arista
	 */
	public Arista getArista(String v1, String v2) {
		Arista res = null;
		for (Arista a : aristas) {
			if (a.contieneVertice(v1) && a.contieneVertice(v2)) {
				res = a;
				break;
			}
		}
		return res;
	}

	/**
	 * Adiciona una arista pasando los vértices extremos
	 * 
	 * @param v1 Vértice 1
	 * @param v2 Vértice 2
	 * @return Arista añadida
	 * @throws Exception Lanzará error si uno de los vértices es null o si ya existe
	 *                   una arista con esos extremos
	 */
	public Arista adicionarArista(Vertice v1, Vertice v2) throws Exception {
		if (v1 == null || v2 == null)
			throw new Exception("Uno o ambos vértices son nulos");
		Arista a = getArista(v1.getNombre(), v2.getNombre());
		if (a != null)
			throw new Exception("Ya existe una arista entre los dos vértices");
		a = new Arista(v1, v2);
		aristas.add(a);
		return a;
	}

	/**
	 * Adiciona una arista pasando los nombres de los vértices extremos
	 * 
	 * @param v1 Nombre del vértice 1
	 * @param v2 Nombre del vértice 2
	 * @return Arista añadida
	 * @throws Exception Lanza un error si uno de los vértices no existe o si ya
	 *                   existe una arista con esos vértices
	 */
	public Arista adicionarArista(String v1, String v2) throws Exception {
		return adicionarArista(getVertice(v1), getVertice(v2));
	}

	/**
	 * Elimina una arista buscándola por los nombres de los vértices extremos
	 * 
	 * @param v1 Nombre del vértice 1
	 * @param v2 NOmbre del vértice 2
	 */
	public void eliminarArista(String v1, String v2) {
		int pos = -1;
		for (int i = 0; i < aristas.size(); i++) {
			Arista a = aristas.get(i);
			if (a.contieneVertice(v1) && a.contieneVertice(v2)) {
				pos = i;
				break;
			}
		}
		if (pos >= 0) {
			aristas.remove(pos);
		}
	}

	/**
	 * Elimina una arista
	 * 
	 * @param a Arista
	 */
	public void eliminarArista(Arista a) {
		eliminarArista(a.getVertice1().getNombre(), a.getVertice2().getNombre());
	}

	/**
	 * Elimina todas las aristas que contienen un determinado vértice
	 * 
	 * @param v Nombre del vértice
	 */
	public void eliminarAristaConVertice(String v) {
		int i = 0;
		int total = aristas.size();
		while (i < total) {
			Arista a = aristas.get(i);
			if (a.contieneVertice(v)) {
				aristas.remove(i);
				total--;
			} else {
				i++;
			}
		}
	}

	/**
	 * Elimina un vértice buscándolo por su nombre
	 * 
	 * @param nombre Nombre del vértice
	 */
	public void eliminarVertice(String nombre) {
		int pos = -1;
		for (int i = 0; i < vertices.size(); i++) {
			Vertice v = vertices.get(i);
			if (v.getNombre().equals(nombre)) {
				pos = i;
				break;
			}
		}
		if (pos >= 0) {
			vertices.remove(pos);
			eliminarAristaConVertice(nombre);
		}
	}

	@Override
	/**
	 * Devuelve información del grafo
	 */
	public String toString() {
		return "Grafo";
	}

	/**
	 * Devuelve una instancia de RutaMinimaGrafo con la ruta mínima calculada entre
	 * dos vértices
	 * 
	 * @param v1 Nombre del vértice 1
	 * @param v2 Nombre del vértice 2
	 * @return Instancia de RutaMinimaGrafo
	 * @throws Exception Lanza un error si ocurre un error en el cálculo de la ruta
	 *                   mínima
	 */
	public RutaMinimaGrafo getRutaMinima(String v1, String v2) throws Exception {
		RutaMinimaGrafo res = new RutaMinimaGrafo(this, v1, v2);
		res.calcular();
		return res;
	}

	/**
	 * Devuelve una instancia de RutaMinimaGrafo con la ruta mínima calculada entre
	 * dos vértices
	 * 
	 * @param v1 Vértice 1
	 * @param v2 Vértice 2
	 * @return Instancia de RutaMinimaGrafo
	 * @throws Exception Exception Lanza un error si ocurre un error en el cálculo
	 *                   de la ruta mínima o si uno o ambos vértices son nulos
	 */
	public RutaMinimaGrafo getRutaMinima(Vertice v1, Vertice v2) throws Exception {
		if (v1 == null || v2 == null)
			throw new Exception("Uno o ambos vértices son nulos");
		return getRutaMinima(v1.getNombre(), v2.getNombre());
	}

	/**
	 * Devuelve las aristas que contienen un vértice
	 * 
	 * @param v Nombre del vértice
	 * @return Lista de aristas encontradas
	 */
	public List<Arista> getAristasConVertice(String v) {
		List<Arista> res = new ArrayList<Arista>();
		for (Arista a : aristas) {
			if (a.contieneVertice(v)) {
				res.add(a);
			}
		}
		return res;
	}

	/**
	 * Devuelve la lista de nombres de vértices conectados con un vértice dado
	 * 
	 * @param v Vertice a buscar conexiones
	 * @return Lista de vértices conectados con "v"
	 */
	public List<String> getVerticesConectadosCon(String v) {
		List<Arista> aristas = getAristasConVertice(v);
		List<String> res = new ArrayList<String>();
		for (Arista a : aristas) {
			String otro = a.getVertice1().getNombre().equals(v) ? a.getVertice2().getNombre()
					: a.getVertice1().getNombre();
			res.add(otro);
		}
		return res;
	}
}
