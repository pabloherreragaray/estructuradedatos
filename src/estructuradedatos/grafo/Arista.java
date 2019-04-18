package estructuradedatos.grafo;

public class Arista {
	private Vertice vertice1;
	private Vertice vertice2;
	private double distancia;

	public Arista(Vertice v1, Vertice v2, double distancia) {
		setVertice1(v1);
		setVertice2(v2);
		setDistancia(distancia);
	}

	public Arista(Vertice v1, Vertice v2) {
		this(v1, v2, Vertice.distanciaEntreVertices(v1, v2));
	}

	public Vertice getVertice1() {
		return vertice1;
	}

	public void setVertice1(Vertice vertice1) {
		this.vertice1 = vertice1;
	}

	public Vertice getVertice2() {
		return vertice2;
	}

	public void setVertice2(Vertice vertice2) {
		this.vertice2 = vertice2;
	}

	public double getDistancia() {
		return distancia;
	}

	public void setDistancia(double distancia) {
		this.distancia = distancia;
	}

	public boolean contieneVertice(String v) {
		return getVertice1().getNombre().equals(v) || getVertice2().getNombre().equals(v);
	}

	@Override
	public String toString() {
		return "Arista ".concat(getVertice1().getNombre()).concat(" <-> ").concat(getVertice2().getNombre())
				.concat(" (").concat(String.valueOf(getDistancia())).concat(")");
	}
}
