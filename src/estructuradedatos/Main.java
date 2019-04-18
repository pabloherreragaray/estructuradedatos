package estructuradedatos;

import estructuradedatos.grafo.Arista;
import estructuradedatos.grafo.Grafo;
import estructuradedatos.grafo.RutaMinimaGrafo;
import estructuradedatos.grafo.Vertice;

public class Main {

	public static void main(String[] args) {
		Grafo g = new Grafo();
		try {
			g.adicionarVertice(10, 10);
			g.adicionarVertice(100, 100);
			g.adicionarVertice(0, 10);
			g.adicionarVertice(10, 0);
			g.adicionarVertice(0, 100);
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			g.adicionarArista("A", "B");
			g.adicionarArista("B", "C");
			g.adicionarArista("C", "D");
			g.adicionarArista("D", "E");
			g.adicionarArista("E", "C");
			g.adicionarArista("B", "D");
			g.adicionarArista("D", "A");
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(mostrarGrafo(g));
		try {
			RutaMinimaGrafo rm = g.getRutaMinima("A", "E");
			for (String s : rm.getVertices()) {
				System.out.println(s);
			}
			System.out.println(rm.getDistancia());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String mostrarGrafo(Grafo g) {
		StringBuilder sb = new StringBuilder();
		sb.append("VERTICES\n");
		for (Vertice v : g.getVertices()) {
			sb.append(v.toString()).append("\n");
		}
		sb.append("ARISTAS\n");
		for (Arista a : g.getAristas()) {
			sb.append(a.toString()).append("\n");
		}
		return sb.toString();
	}

}
