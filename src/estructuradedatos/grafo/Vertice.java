package estructuradedatos.grafo;

/**
 * Clase que representa un vértice
 * 
 * @author Pablo Herrera
 *
 */
public class Vertice {
	private static int contadorLetra = 0;

	private String nombre;
	private double x;
	private double y;

	/**
	 * Constructor pasando su nombre y coordenadas
	 * 
	 * @param nombre Nombre
	 * @param x      X
	 * @param y      Y
	 */
	public Vertice(String nombre, double x, double y) {
		setNombre(nombre);
		setX(x);
		setY(y);
	}

	/**
	 * Constructor pasando su coordenadas
	 * 
	 * @param x X
	 * @param y Y
	 */
	public Vertice(double x, double y) {
		this(getNombrePorDefecto(), x, y);
	}

	/**
	 * Devuelve el nombre del vértice
	 * 
	 * @return Nombre
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * Modifica el nombre
	 * 
	 * @param nombre Nombre
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * Devuelve la X
	 * 
	 * @return X
	 */
	public double getX() {
		return x;
	}

	/**
	 * Modifica la X
	 * 
	 * @param x C
	 */
	public void setX(double x) {
		this.x = x;
	}

	/**
	 * Devuelve la Y
	 * 
	 * @return Y
	 */
	public double getY() {
		return y;
	}

	/**
	 * Modifica la Y
	 * 
	 * @param y Y
	 */
	public void setY(double y) {
		this.y = y;
	}

	/**
	 * Devuelve un nombre por defecto para un vértice
	 * 
	 * @return Nombre por defecto
	 */
	public static String getNombrePorDefecto() {
		String letras = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		String letra;
		if (contadorLetra >= letras.length()) {
			letra = String.valueOf(letras.charAt(contadorLetra % letras.length()))
					.concat(String.valueOf((int) (contadorLetra / letras.length())));
		} else {
			letra = String.valueOf(letras.charAt(contadorLetra));
		}
		contadorLetra++;
		return letra;
	}

	/**
	 * Devuelve la distancia entre dos vértices
	 * 
	 * @param v1 Vértice 1
	 * @param v2 Vértice 2
	 * @return Distancia entre vértices
	 */
	public static double distanciaEntreVertices(Vertice v1, Vertice v2) {
		double res = 0;
		if (v1 != null && v2 != null) {
			res = Math.sqrt(Math.pow(v2.getX() - v1.getX(), 2) + Math.pow(v2.getY() - v1.getY(), 2));
		}
		return res;
	}

	@Override
	/**
	 * Devuelve información del vértice
	 */
	public String toString() {
		return "Vértice ".concat(getNombre()).concat(" (").concat(String.valueOf(getX())).concat(", ")
				.concat(String.valueOf(getY())).concat(")");
	}

	public static int getContadorLetra() {
		return contadorLetra;
	}

	public static void setContadorLetra(int contadorLetra) {
		Vertice.contadorLetra = contadorLetra;
	}
}
