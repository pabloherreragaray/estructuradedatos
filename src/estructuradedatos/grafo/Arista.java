package estructuradedatos.grafo;

/**
 * Clase que representa una arista
 * 
 * @author Pablo Herrera
 *
 */
public class Arista {
	private Vertice vertice1;
	private Vertice vertice2;
	private double distancia;

	/**
	 * Constructor pasando dos v�rtices y distancia
	 * 
	 * @param v1        V�rtice 1
	 * @param v2        V�rtice 2
	 * @param distancia Distancia
	 */
	public Arista(Vertice v1, Vertice v2, double distancia) {
		setVertice1(v1);
		setVertice2(v2);
		setDistancia(distancia);
	}

	/**
	 * Constructor pasando dos v�rtices. La distancia se calcula
	 * 
	 * @param v1 V�rtice 1
	 * @param v2 V�rtice 2
	 */
	public Arista(Vertice v1, Vertice v2) {
		this(v1, v2, Vertice.distanciaEntreVertices(v1, v2));
	}

	/**
	 * Devuelve el v�rtice 1
	 * 
	 * @return V�rtice 1
	 */
	public Vertice getVertice1() {
		return vertice1;
	}

	/**
	 * Modifica el v�rtice 1
	 * 
	 * @param vertice1 V�rtice 1
	 */
	public void setVertice1(Vertice vertice1) {
		this.vertice1 = vertice1;
	}

	/**
	 * Devuelve el v�rtice 2
	 * 
	 * @return V�rtice 2
	 */
	public Vertice getVertice2() {
		return vertice2;
	}

	/**
	 * Modifica el v�rtice 2
	 * 
	 * @param vertice2 V�rtice 2
	 */
	public void setVertice2(Vertice vertice2) {
		this.vertice2 = vertice2;
	}

	/**
	 * Devuelve la distancia
	 * 
	 * @return Distancia
	 */
	public double getDistancia() {
		return distancia;
	}

	/**
	 * Modifica la distancia
	 * 
	 * @param distancia Distancia
	 */
	public void setDistancia(double distancia) {
		this.distancia = distancia;
	}

	/**
	 * Devuelve true si la arista contiene el v�rtice buscado
	 * 
	 * @param v Nombre del v�rtice
	 * @return true si la arsita contiene el v�rtice
	 */
	public boolean contieneVertice(String v) {
		return getVertice1().getNombre().equals(v) || getVertice2().getNombre().equals(v);
	}

	@Override
	/**
	 * Devuelve informaci�n de la arista
	 */
	public String toString() {
		return "Arista ".concat(getVertice1().getNombre()).concat(" <-> ").concat(getVertice2().getNombre())
				.concat(" (").concat(String.valueOf(getDistancia())).concat(")");
	}
}
