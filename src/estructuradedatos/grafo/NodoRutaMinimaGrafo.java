package estructuradedatos.grafo;

public class NodoRutaMinimaGrafo {
	private String vertice;
	private double distancia;
	private boolean definitivo;
	private NodoRutaMinimaGrafo predecesor;

	public NodoRutaMinimaGrafo(String v) {
		vertice = v;
		distancia = Double.MAX_VALUE;
	}

	public double getDistancia() {
		return distancia;
	}

	public void setDistancia(double distancia) {
		this.distancia = distancia;
	}

	public boolean isDefinitivo() {
		return definitivo;
	}

	public void setDefinitivo(boolean definitivo) {
		this.definitivo = definitivo;
	}

	public NodoRutaMinimaGrafo getPredecesor() {
		return predecesor;
	}

	public void setPredecesor(NodoRutaMinimaGrafo predecesor) {
		this.predecesor = predecesor;
	}

	public String getVertice() {
		return vertice;
	}
}
