package estructuradedatos.grafo;

/**
 * Clase que almacena informaci�n para el c�lculo de la ruta m�nima
 * 
 * @author Pablo Herrera
 *
 */
public class NodoRutaMinimaGrafo {
	private String vertice;
	private double distancia;
	private boolean definitivo;
	private NodoRutaMinimaGrafo predecesor;

	/**
	 * Constructor
	 * 
	 * @param v Nombre del v�rtice
	 */
	public NodoRutaMinimaGrafo(String v) {
		vertice = v;
		distancia = Double.MAX_VALUE;
	}

	/**
	 * Devuelve la distancia temporal
	 * 
	 * @return Distancia temporal
	 */
	public double getDistancia() {
		return distancia;
	}

	/**
	 * Modifica la distancia temporal
	 * 
	 * @param distancia Distancia temporal
	 */
	public void setDistancia(double distancia) {
		this.distancia = distancia;
	}

	/**
	 * Devuelve true si es un nodo definitivo
	 * 
	 * @return true si es un nodo definitivo
	 */
	public boolean isDefinitivo() {
		return definitivo;
	}

	/**
	 * Modifica si es un nodo definitivo
	 * 
	 * @param definitivo true si es un nodo definitivo
	 */
	public void setDefinitivo(boolean definitivo) {
		this.definitivo = definitivo;
	}

	/**
	 * Devuelve el predecesor
	 * 
	 * @return Predecesor
	 */
	public NodoRutaMinimaGrafo getPredecesor() {
		return predecesor;
	}

	/**
	 * Modifica el predecesor
	 * 
	 * @param predecesor Predecesor
	 */
	public void setPredecesor(NodoRutaMinimaGrafo predecesor) {
		this.predecesor = predecesor;
	}

	/**
	 * Devuelve el nombre del v�rtice
	 * 
	 * @return Nombre del v�rtice
	 */
	public String getVertice() {
		return vertice;
	}
}
