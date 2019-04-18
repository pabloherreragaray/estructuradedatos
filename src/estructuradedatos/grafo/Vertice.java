package estructuradedatos.grafo;

public class Vertice {
	private static int contadorLetra = 0;

	private String nombre;
	private double x;
	private double y;

	public Vertice(String nombre, double x, double y) {
		setNombre(nombre);
		setX(x);
		setY(y);
	}

	public Vertice(double x, double y) {
		this(getNombrePorDefecto(), x, y);
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

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

	public static double distanciaEntreVertices(Vertice v1, Vertice v2) {
		double res = 0;
		if (v1 != null && v2 != null) {
			res = Math.sqrt(Math.pow(v2.getX() - v1.getX(), 2) + Math.pow(v2.getY() - v1.getY(), 2));
		}
		return res;
	}

	@Override
	public String toString() {
		return "Vértice ".concat(getNombre()).concat(" (").concat(String.valueOf(getX())).concat(", ")
				.concat(String.valueOf(getY())).concat(")");
	}
}
