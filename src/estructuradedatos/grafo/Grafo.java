package estructuradedatos.grafo;

import java.util.ArrayList;
import java.util.List;

public class Grafo {
	private List<Vertice> vertices;
	private List<Arista> aristas;

	public Grafo() {
		vertices = new ArrayList<Vertice>();
		aristas = new ArrayList<Arista>();
	}

	public List<Vertice> getVertices() {
		return vertices;
	}

	public List<Arista> getAristas() {
		return aristas;
	}

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

	public Vertice adicionarVertice(Vertice v) throws Exception {
		if (v == null)
			throw new Exception("El vértice es nulo");
		if (getVertice(v.getNombre()) != null)
			throw new Exception("Ya existe un vértice con el nombre " + v.getNombre());
		vertices.add(v);
		return v;
	}

	public Vertice adicionarVertice(String nombre, double x, double y) throws Exception {
		return adicionarVertice(new Vertice(nombre, x, y));
	}

	public Vertice adicionarVertice(double x, double y) throws Exception {
		return adicionarVertice(new Vertice(x, y));
	}

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

	public Arista adicionarArista(String v1, String v2) throws Exception {
		return adicionarArista(getVertice(v1), getVertice(v2));
	}

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

	public void eliminarArista(Arista a) {
		eliminarArista(a.getVertice1().getNombre(), a.getVertice2().getNombre());
	}

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

	public void elimiarVertice(String nombre) {
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
	public String toString() {
		return "Grafo";
	}

	public RutaMinimaGrafo getRutaMinima(String v1, String v2) throws Exception {
		if (v1 == null || v2 == null)
			throw new Exception("Uno o ambos vértices son nulos");
		RutaMinimaGrafo res = new RutaMinimaGrafo(this, v1, v2);
		res.calcular();
		return res;
	}

	public RutaMinimaGrafo getRutaMinima(Vertice v1, Vertice v2) throws Exception {
		return getRutaMinima(v1.getNombre(), v2.getNombre());
	}

	public List<Arista> getAristasConVertice(String v) {
		List<Arista> res = new ArrayList<Arista>();
		for (Arista a : aristas) {
			if (a.contieneVertice(v)) {
				res.add(a);
			}
		}
		return res;
	}
}
