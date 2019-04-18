package estructuradedatos.grafo;

import java.util.ArrayList;
import java.util.List;

public class RutaMinimaGrafo {
	private Grafo grafo;
	private String verticeInicial;
	private String verticeFinal;
	private List<String> vertices;
	private double distancia;
	private List<NodoRutaMinimaGrafo> matrizNodos;
	private NodoRutaMinimaGrafo nodoInicial;
	private NodoRutaMinimaGrafo nodoFinal;

	public RutaMinimaGrafo(Grafo g, String vInicial, String vFinal) {
		grafo = g;
		verticeInicial = vInicial;
		verticeFinal = vFinal;
	}

	public Grafo getGrafo() {
		return grafo;
	}

	public String getVerticeInicial() {
		return verticeInicial;
	}

	public String getVerticeFinal() {
		return verticeFinal;
	}

	public List<String> getVertices() {
		return vertices;
	}

	public double getDistancia() {
		return distancia;
	}

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
			throw new Exception("La matriz de nodos está vacía");
		else if (nodoInicial == null)
			throw new Exception("No se encontró el nodo inicial");
		else if (nodoFinal == null)
			throw new Exception("No se encontró el nodo final");
		nodoInicial.setDistancia(0);
		nodoInicial.setDefinitivo(true);
		NodoRutaMinimaGrafo nodoActual = nodoInicial;
		int nodosDefinitivos = 1;
		while (nodosDefinitivos < matrizNodos.size()) {
			List<Arista> aristas = getGrafo().getAristasConVertice(nodoActual.getVertice());
			if (aristas.size() == 0)
				break;
			NodoRutaMinimaGrafo nodoMinimo = null;
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
							if (nodoMinimo == null || nodoMinimo.getDistancia() > nodo.getDistancia()) {
								nodoMinimo = nodo;
							}
						}
						break;
					}
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
			throw new Exception("No se encontó una ruta entre ambos vértices");
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
}
