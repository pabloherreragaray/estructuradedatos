package estructuradedatos.grafo;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase que calcula y devuelve una ruta m�nima
 * 
 * @author Pablo Herrera
 *
 */
public class RutaMinimaGrafo {
	private Grafo grafo;
	private String verticeInicial;
	private String verticeFinal;
	private List<String> vertices;
	private double distancia;
	private List<NodoRutaMinimaGrafo> matrizNodos;
	private NodoRutaMinimaGrafo nodoInicial;
	private NodoRutaMinimaGrafo nodoFinal;

	/**
	 * Constructor
	 * 
	 * @param g        Grafo
	 * @param vInicial Nombre del v�rtice inicial
	 * @param vFinal   Nombre del v�rtice final
	 */
	public RutaMinimaGrafo(Grafo g, String vInicial, String vFinal) {
		grafo = g;
		verticeInicial = vInicial;
		verticeFinal = vFinal;
	}

	/**
	 * Devuelve el grafo
	 * 
	 * @return Grafo
	 */
	public Grafo getGrafo() {
		return grafo;
	}

	/**
	 * Devuelve el v�rtice inicial
	 * 
	 * @return Nombre del v�rtice inicial
	 */
	public String getVerticeInicial() {
		return verticeInicial;
	}

	/**
	 * Devuelve el v�rtice final
	 * 
	 * @return Nombre del v�rtice final
	 */
	public String getVerticeFinal() {
		return verticeFinal;
	}

	/**
	 * Devuelve los v�rtices de la ruta m�nima
	 * 
	 * @return Lista de nombres de los v�rtices de la ruta m�nima
	 */
	public List<String> getVertices() {
		return vertices;
	}

	/**
	 * Devuelve la distancia de la ruta m�nima
	 * 
	 * @return Distancia de la ruta m�nima
	 */
	public double getDistancia() {
		return distancia;
	}

	/**
	 * Calcula la ruta m�nima
	 * 
	 * @throws Exception Lnza error si no se encontr� los v�rtices inicial o final,
	 *                   o si no hay una ruta entre los dos v�rtices
	 */
	public void calcular() throws Exception {
		matrizNodos = new ArrayList<NodoRutaMinimaGrafo>();
		for (Vertice v : getGrafo().getVertices()) {
			NodoRutaMinimaGrafo nodo = new NodoRutaMinimaGrafo(v.getNombre());
			matrizNodos.add(nodo);
			if (v.getNombre().equals(verticeInicial))
				nodoInicial = nodo;
			else if (v.getNombre().equals(verticeFinal))
				nodoFinal = nodo;
		}
		if (matrizNodos.size() == 0)
			throw new Exception("La matriz de nodos est� vac�a");
		else if (nodoInicial == null)
			throw new Exception("No se encontr� el nodo inicial");
		else if (nodoFinal == null)
			throw new Exception("No se encontr� el nodo final");
		nodoInicial.setDistancia(0);
		nodoInicial.setDefinitivo(true);
		NodoRutaMinimaGrafo nodoActual = nodoInicial;
		int nodosDefinitivos = 1;
		while (nodosDefinitivos < matrizNodos.size()) {
			List<Arista> aristas = getGrafo().getAristasConVertice(nodoActual.getVertice());
			if (aristas.size() == 0)
				break;
			for (Arista a : aristas) {
				double distanciaArista = a.getDistancia() + nodoActual.getDistancia();
				String otro = a.getVertice1().getNombre().equals(nodoActual.getVertice()) ? a.getVertice2().getNombre()
						: a.getVertice1().getNombre();
				for (NodoRutaMinimaGrafo nodo : matrizNodos) {
					if (nodo.getVertice().equals(otro)) {
						if (!nodo.isDefinitivo()) {
							if (nodo.getDistancia() > distanciaArista) {
								nodo.setDistancia(distanciaArista);
								nodo.setPredecesor(nodoActual);
							}
						}
						break;
					}
				}
			}
			NodoRutaMinimaGrafo nodoMinimo = null;
			for (NodoRutaMinimaGrafo nodo : matrizNodos) {
				if (!nodo.isDefinitivo() && nodo.getDistancia() < Double.MAX_VALUE
						&& (nodoMinimo == null || nodoMinimo.getDistancia() > nodo.getDistancia())) {
					nodoMinimo = nodo;
				}
			}
			if (nodoMinimo == null)
				break;
			nodoActual = nodoMinimo;
			nodoActual.setDefinitivo(true);
			if (nodoActual == nodoFinal)
				break;
			nodosDefinitivos++;
		}
		if (!nodoFinal.isDefinitivo())
			throw new Exception("No se encont� una ruta entre ambos v�rtices");
		List<String> temp = new ArrayList<String>();
		distancia = nodoFinal.getDistancia();
		while (nodoFinal != null) {
			temp.add(nodoFinal.getVertice());
			nodoFinal = nodoFinal.getPredecesor();
		}
		vertices = new ArrayList<String>();
		for (int i = temp.size() - 1; i >= 0; i--) {
			vertices.add(temp.get(i));
		}
	}

	/**
	 * Devuelve la lista de aristas implicadas en la ruta m�nima, en el orden del
	 * recorrido
	 * 
	 * @return Lista de aristas de la ruta m�nima
	 */
	public List<Arista> getListaAristas() {
		List<Arista> res = new ArrayList<Arista>();
		if (vertices != null && vertices.size() >= 2) {
			String va = null;
			for (String v : vertices) {
				if (va != null) {
					Arista a = grafo.getArista(v, va);
					res.add(a);
				}
				va = v;
			}
		}
		return res;
	}
}
