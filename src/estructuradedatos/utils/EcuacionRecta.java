package estructuradedatos.utils;

public class EcuacionRecta {

	private double m;
	private double b;

	public EcuacionRecta(double m, double b) {
		setM(m);
		setB(b);
	}

	public double getM() {
		return m;
	}

	public void setM(double m) {
		this.m = m;
	}

	public double getB() {
		return b;
	}

	public void setB(double b) {
		this.b = b;
	}

	public static EcuacionRecta desdeMB(double m, double b) {
		return new EcuacionRecta(m, b);
	}

	public static EcuacionRecta desdeDosPuntos(double x1, double y1, double x2, double y2) {
		return desdeMB((y2 - y1) / (x2 - x1), y1);
	}

	public double getCoefA() {
		return m;
	}

	public double getCoefB() {
		return -1;
	}

	public double getCoefC() {
		return b;
	}

	public double getDistanciaPunto(double x, double y) {
		return Math.abs((getCoefA() * x + getCoefB() * y + getCoefC())
				/ Math.sqrt(Math.pow(getCoefA(), 2) + Math.pow(getCoefB(), 2)));
	}
	
	public static double distanciaEntrePuntos(double x1, double y1, double x2, double y2) {
		return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
	}

}
